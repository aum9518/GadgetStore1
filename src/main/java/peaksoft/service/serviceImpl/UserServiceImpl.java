package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JWTService;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoBasket.GetAllUsersBasketResponse;
import peaksoft.dto.dtoFavorite.FavoriteResponse;
import peaksoft.dto.dtoUser.UserRequest;
import peaksoft.dto.dtoUser.UserResponse;
import peaksoft.entity.Basket;
import peaksoft.entity.Favorite;
import peaksoft.entity.Product;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.BasketRepository;
import peaksoft.repository.FavoriteRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final FavoriteRepository favoriteRepository;
    private final JWTService jwtService;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = repository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email:" + email + " is not found!"));
        return user;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return repository.getAllUsers();
    }


    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.firsName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setCreatedAt(ZonedDateTime.now());
        user.setUpdateDate(null);
        user.setRole(Role.USER);
        repository.save(user);

        Basket basket = new Basket();
        basket.setUser(user);
        basketRepository.save(basket);


        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("User with name: " + user.getFirstName() + " successfully saved")
                .build();
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + "is not found"));
        user.setFirstName(userRequest.firsName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setUpdateDate(ZonedDateTime.now());
        repository.save(user);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("User with name: " + user.getFirstName() + " successfully updated")
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + "is not found"));
        String token = jwtService.generateToken(user);
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .createAt(user.getCreatedAt())
                .updatedAt(user.getUpdateDate())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else throw new NotFoundException("user with id: " + id + "is not found");
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }

    @Override
    public SimpleResponse addOrDeleteFavorite(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product with id: " + productId + " is not found!"));
        Product newProduct = Product.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .images(product.getImages())
                .isFavorite(true)
                .characteristic(product.getCharacteristic())
                .madeIn(product.getMadeIn())
                .category(product.getCategory())
                .build();


            User user = getAuthentication();
            List<Favorite> all = favoriteRepository.findAll();
        for (Favorite f:all) {
            if (f.getUser().equals(user)){
                if (favoriteRepository.existsById(f.getId())){
                favoriteRepository.deleteById(f.getId());
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully deleted")
                        .build();
                }
            }
        }
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(newProduct);
        favoriteRepository.save(favorite);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully added")
                .build();


    }

    @Override
    public List<FavoriteResponse> getAllUsersFavorites() {
        User user = getAuthentication();
        List<Favorite> all = favoriteRepository.findAll();
        List<FavoriteResponse> favoriteResponseList = new ArrayList<>();
        for (Favorite f : all) {
            if (f.getUser().equals(user)) {
                FavoriteResponse response = FavoriteResponse.builder()
                        .id(f.getId())
                        .userName(f.getUser().getFirstName())
                        .productName(f.getProduct().getName())
                        .build();
                favoriteResponseList.add(response);
            }else throw new BadCredentialException(" The user's token does not match");
        }
        return favoriteResponseList;
    }

    @Override
    public SimpleResponse addOrDeleteProductsToBasket(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product with id: " + productId + " is not found!"));
        User user = getAuthentication();
        List<Basket> allBaskets = basketRepository.findAll();
            for (Basket b : allBaskets) {
                if (b.getUser().equals(user)) {

                    if (b.getProducts().contains(product)){

                                for (Product p : b.getProducts()) {
                                    if (p.equals(product)) {
                                        b.getProducts().remove(p);
                                        return SimpleResponse.builder()
                                                .status(HttpStatus.OK)
                                                .message("Successfully deleted!")
                                                .build();
                                    }

                                b.getProducts().remove(product);
                                product.getBaskets().remove(b);
                            }

                    } else{
                        product.getBaskets().add(b);
                        b.getProducts().add(product);
                        basketRepository.save(b);
                        productRepository.save(product);
                        return SimpleResponse.builder()
                                .status(HttpStatus.OK)
                                .message("Successfully added")
                                .build();
                    }

                }
            }

        return null;
    }

    @Override
    public GetAllUsersBasketResponse getUsersAllBasketsProduct() {
        User user = getAuthentication();
        List<Basket> allBaskets = basketRepository.findAll();
        List<Product> productList = new ArrayList<>();
        int sum = 0;
        int Quantity = 0;
        Product product = new Product();
        for (Basket allBasket : allBaskets) {
            if (allBasket.getUser().equals(user)) {
                Basket basket = new Basket();
                basket.setId(allBasket.getId());
                basket.setProducts(allBasket.getProducts());
                basket.setUser(allBasket.getUser());
                for (int i = 0; i < basket.getProducts().size(); i++) {
                    product.setId(basket.getProducts().get(i).getId());
                    product.setName(basket.getProducts().get(i).getName());
                    product.setPrice(basket.getProducts().get(i).getPrice());
                    product.setImages(basket.getProducts().get(i).getImages());
                    product.setCharacteristic(basket.getProducts().get(i).getCharacteristic());
                    product.setMadeIn(basket.getProducts().get(i).getMadeIn());
                    product.setCategory(basket.getProducts().get(i).getCategory());
                    productList.add(product);
                    sum = sum + product.getPrice();
                }
            }
        }
        return GetAllUsersBasketResponse.builder()
                .products(productList)
                .sizeOfProducts(productList.size())
                .sumOfPrice(sum)
                .build();
    }

    @Override
    public SimpleResponse deleteAllProductsFromBasket() {
        User user = getAuthentication();
        List<Basket> allBaskets = basketRepository.findAll();

        List<Product> products = new ArrayList<>();
        for (Basket b : allBaskets) {
            if (b.getUser().equals(user)) {
                repository.deleteAllBasketsProducts(b.getId(),products);
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully cleaned!")
                        .build();
            }
        }
        return null;
    }


}
