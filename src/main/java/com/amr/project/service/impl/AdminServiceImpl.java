package com.amr.project.service.impl;

import com.amr.project.converter.*;
import com.amr.project.model.dto.AdminPageDto;
import com.amr.project.model.entity.*;
import com.amr.project.service.abstracts.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final CountryService countryService;
    private final CityService cityService;
    private final AddressService addressService;
    private final CategoryService categoryService;
    private final ShopService shopService;
    private final ItemService itemService;
    private final UserService userService;
    private final RoleService roleService;

    public AdminServiceImpl(CountryService countryService, CityService cityService, AddressService addressService, CategoryService categoryService, ShopService shopService, ItemService itemService, UserService userService, RoleService roleService) {
        this.countryService = countryService;
        this.cityService = cityService;
        this.addressService = addressService;
        this.categoryService = categoryService;
        this.shopService = shopService;
        this.itemService = itemService;
        this.userService = userService;
        this.roleService = roleService;
    }

    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);
    private final CityMapper cityMapper = Mappers.getMapper(CityMapper.class);
    private final AdminAddressMapper addressMapper = Mappers.getMapper(AdminAddressMapper.class);
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    private final ShopMapper shopMapper = Mappers.getMapper(ShopMapper.class);
    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private final AdminUserMapper userMapper = Mappers.getMapper(AdminUserMapper.class);
    private final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @Override
    public AdminPageDto show() {
        return new AdminPageDto(countryMapper.countryListToListCountryDto(countryService.getAll(Country.class)),
                cityMapper.cityListToListAdminCityDto(cityService.getAll(City.class)),
                addressMapper.addressListToListAdminAddressDto(addressService.getAll(Address.class)),
                categoryMapper.categoryListToListCategoryDTO(categoryService.getAll(Category.class)),
                userMapper.userListToListAdminUserDto(userService.getAll(User.class)),
                shopMapper.shopListToListAdminShopDto(shopService.getAll(Shop.class)),
                itemMapper.itemListToListAdminItemDto(itemService.getAll(Item.class)),
                roleMapper.roleListToListRoleDto(roleService.getAll(Role.class)));
    }
}
