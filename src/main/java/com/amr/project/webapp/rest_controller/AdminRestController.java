package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.*;
import com.amr.project.model.dto.*;
import com.amr.project.model.entity.*;
import com.amr.project.service.abstracts.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/adminapi")
public class AdminRestController {
    private final AdminService adminService;
    private final CountryService countryService;
    private final CityService cityService;
    private final AddressService addressService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ShopService shopService;
    private final ItemService itemService;
    private final RoleService roleService;

    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);
    private final CityMapper cityMapper = Mappers.getMapper(CityMapper.class);
    private final AdminAddressMapper addressMapper = Mappers.getMapper(AdminAddressMapper.class);
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    private final ShopMapper shopMapper = Mappers.getMapper(ShopMapper.class);
    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private final AdminUserMapper userMapper = Mappers.getMapper(AdminUserMapper.class);

    public AdminRestController(AdminService adminService, CountryService countryService, CityService cityService,
                               AddressService addressService, CategoryService categoryService, UserService userService,
                               ShopService shopService, ItemService itemService, RoleService roleService) {
        this.adminService = adminService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.addressService = addressService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.shopService = shopService;
        this.itemService = itemService;
        this.roleService = roleService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/adminpagedto")
    public AdminPageDto show() {
        return adminService.show();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/countries")
    public List<CountryDto> showListCountry() {
        return adminService.show().getCountryDtoList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/countries/{id}")
    public CountryDto oneCountry(@PathVariable Long id) {
        return countryMapper.countryToCountryDto(countryService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/countries")
    public Country saveCountry(@RequestBody Country country) {
        countryService.persist(country);
        return country;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/countries")
    public Country updateCountry(@RequestBody Country country) {
        countryService.update(country);
        return country;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/countries/{id}")
    public String deleteCountry(@PathVariable long id) {
        countryService.delete(countryService.findById(id));
        return "" + id;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/cities")
    public List<AdminCityDto> showListCity() {
        return adminService.show().getCityDtoList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/cities/{id}")
    public AdminCityDto oneCity(@PathVariable Long id) {
        return cityMapper.cityToAdminCityDto(cityService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/cities")
    public City saveCity(@RequestBody City city) {
        Long id = Long.parseLong(city.getCountry().getName());
        city.setCountry(countryService.findById(id));
        cityService.persist(city);
        return city;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/cities")
    public City updateCity(@RequestBody City city) {
        Long id = Long.parseLong(city.getCountry().getName());
        city.setCountry(countryService.findById(id));
        cityService.update(city);
        return city;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/cities/{id}")
    public String deleteCity(@PathVariable long id) {
        cityService.delete(cityService.findById(id));
        return "" + id;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/addresses")
    public List<AdminAddressDto> showListAddresses() {
        return adminService.show().getAddressDtoList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/addresses/{id}")
    public AdminAddressDto oneAddress(@PathVariable Long id) {
        return addressMapper.addressToAdminAddressDto(addressService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/addresses")
    public Address saveAddress(@RequestBody Address address) {
        Long id = Long.parseLong(address.getCity().getName());
        address.setCountry(cityService.findById(id).getCountry());
        address.setCity(cityService.findById(id));
        addressService.persist(address);
        return address;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/addresses")
    public Address updateAddress(@RequestBody Address address) {
        Long id = Long.parseLong(address.getCity().getName());
        address.setCountry(cityService.findById(id).getCountry());
        address.setCity(cityService.findById(id));
        addressService.update(address);
        return address;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/addresses/{id}")
    public String deleteAddress(@PathVariable long id) {
        addressService.delete(addressService.findById(id));
        return "" + id;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/categories")
    public List<CategoryDto> showListCategories() {
        return adminService.show().getCategoryDtoList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/categories/{id}")
    public CategoryDto oneCategory(@PathVariable Long id) {
        return categoryMapper.categoryToCategoryDto(categoryService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/categories")
    public Category saveCategory(@RequestBody Category category) {
        categoryService.persist(category);
        return category;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/categories")
    public Category updateCategory(@RequestBody Category category) {
        categoryService.update(category);
        return category;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable long id) {
        categoryService.delete(categoryService.findById(id));
        return "" + id;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<AdminUserDto> showListUsers() {
        return adminService.show().getUserDtoList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public AdminUserDto oneUser(@PathVariable Long id) {
        return userMapper.userToAdminUserDto(userService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.findByName(role.getName()));
        }
        user.setRoles(roles);
        userService.persist(user);
        return user;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/users")
    public User updateUser(@RequestBody User user) {
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.findByName(role.getName()));
        }
        user.setRoles(roles);
        userService.update(user);
        return user;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.delete(userService.findById(id));
        return "" + id;
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/shops")
    public List<AdminShopDto> showListShops() {
        return adminService.show().getShopDtoList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/shops/{id}")
    public AdminShopDto oneShop(@PathVariable Long id) {
        return shopMapper.shopToAdminShopDto(shopService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/shops")
    public Shop saveShop(@RequestBody Shop shop) {
        Long countryId = Long.parseLong(shop.getLocation().getName());
        shop.setLocation(countryService.findById(countryId));
        Long userId = Long.parseLong(shop.getUser().getEmail());
        shop.setUser(userService.findById(userId));
        shopService.persist(shop);
        return shop;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/shops")
    public Shop updateShop(@RequestBody Shop shop) {
        Long countryId = Long.parseLong(shop.getLocation().getName());
        shop.setLocation(countryService.findById(countryId));
        Long userId = Long.parseLong(shop.getUser().getEmail());
        shop.setUser(userService.findById(userId));
        shopService.update(shop);
        return shop;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/shops/{id}")
    public String deleteShop(@PathVariable long id) {
        shopService.delete(shopService.findById(id));
        return "" + id;
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/items")
    public List<AdminItemDto> showListItems() {
        return adminService.show().getItemDtoList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/items/{id}")
    public AdminItemDto oneItem(@PathVariable Long id) {
        return itemMapper.itemToAdminItemDto(itemService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/items")
    public Item saveItem(@RequestBody Item item) {
        Long id = Long.parseLong(item.getShop().getName());
        item.setShop(shopService.findById(id));
        List<Category> categories = new ArrayList<>();
        for (Category category : item.getCategories()) {
            categories.add(categoryService.findByName(category.getName()));
        }
        item.setCategories(categories);
        itemService.persist(item);
        return item;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/items")
    public Item updateItem(@RequestBody Item item) {
        Long id = Long.parseLong(item.getShop().getName());
        item.setShop(shopService.findById(id));
        List<Category> categories = new ArrayList<>();
        for (Category category : item.getCategories()) {
            categories.add(categoryService.findByName(category.getName()));
        }
        item.setCategories(categories);
        itemService.update(item);
        return item;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/items/{id}")
    public String deleteItem(@PathVariable long id) {
        itemService.delete(itemService.findById(id));
        return "" + id;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/roles")
    public List<RoleDto> showListRoles() {
        return adminService.show().getRoleDtoList();
    }





}
