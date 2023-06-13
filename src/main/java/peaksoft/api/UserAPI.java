package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoBasket.GetAllUsersBasketResponse;
import peaksoft.dto.dtoFavorite.FavoriteResponse;
import peaksoft.dto.dtoUser.UserRequest;
import peaksoft.dto.dtoUser.UserResponse;
import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers(){
        return service.getAllUsers();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveUser(@RequestBody UserRequest userRequest){
        return service.saveUser(userRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PutMapping("{id}")
    public SimpleResponse updateUser(@PathVariable Long id,@RequestBody UserRequest userRequest){
        return service.updateUser(id, userRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @DeleteMapping("{id}")
    public SimpleResponse deleteUserById(@PathVariable Long id){
        return service.deleteUserById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add")
    public SimpleResponse deleteOrAddProductsToFavorite(@RequestParam Long id){
        return service.addOrDeleteFavorite(id);
    }

    @GetMapping("/favorites")
    public List<FavoriteResponse> getAllUsersFavorites(){
        return service.getAllUsersFavorites();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/basket")
    public SimpleResponse deleteOrAddProductsToBasket(@RequestParam Long id){
        return service.addOrDeleteProductsToBasket(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/basketsProduct")
    public GetAllUsersBasketResponse getAllUsersProductFromBasket(){
        return service.getUsersAllBasketsProduct();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/clean")
    public SimpleResponse deleteUsersProductFromBasket(){
        return service.deleteAllProductsFromBasket();
    }
}
