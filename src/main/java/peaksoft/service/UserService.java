package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoBasket.GetAllUsersBasketResponse;
import peaksoft.dto.dtoFavorite.FavoriteResponse;
import peaksoft.dto.dtoUser.UserRequest;
import peaksoft.dto.dtoUser.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    SimpleResponse saveUser(UserRequest userRequest);
    SimpleResponse updateUser(Long id, UserRequest userRequest);
    UserResponse getUserById(Long id);
    SimpleResponse deleteUserById(Long id);
    SimpleResponse addOrDeleteFavorite(Long productId);
    List<FavoriteResponse> getAllUsersFavorites();
    SimpleResponse addOrDeleteProductsToBasket(Long productId);
    GetAllUsersBasketResponse getUsersAllBasketsProduct();
    SimpleResponse deleteAllProductsFromBasket();

}
