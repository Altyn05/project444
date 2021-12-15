//    package com.amr.project;
//
//import com.amr.project.dao.abstracts.*;
//import com.amr.project.model.entity.*;
//import com.amr.project.model.enums.Gender;
//import com.amr.project.model.enums.Status;
//import com.amr.project.service.abstracts.ReadWriteService;
//import com.amr.project.service.abstracts.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Component
//public class DatabaseInitializer {
//
//    private final UserService userService;
//
//    private final RoleDao roleDao;
//    private final UserDao userDao;
//    private final AddressDao addressDao;
//    private final CountryDao countryDao;
//    private final CityDao cityDao;
//    private final CategoryDao categoryDao;
//    private final ItemDao itemDao;
//    private final ImageDao imageDao;
//    private final ShopDao shopDao;
//    private final ReviewDao reviewDao;
//    private final ReadWriteDao <Order, Long> rwOrder;
//
//    private List<Category> categories;
//    private List<Country> countries;
//    private List<City> cities;
//    private Set<Role> roles;
//    private List<User> users;
//    private List<Item> items;
//    private List<Shop> shops;
//    private List<Review> reviews;
//    private List<Order> orders;
//
//
//    @Autowired
//    public DatabaseInitializer(RoleDao roleDao, UserDao userDao, AddressDao addressDao,
//                               CountryDao countryDao, CityDao cityDao, CategoryDao categoryDao,
//                               ItemDao itemDao, ImageDao imageDao, ShopDao shopDao, ReviewDao reviewDao, UserService userService, ReadWriteDao <Order, Long> rwOrder) {
//
//        this.roleDao = roleDao;
//        this.userDao = userDao;
//        this.addressDao = addressDao;
//        this.countryDao = countryDao;
//        this.cityDao = cityDao;
//        this.categoryDao = categoryDao;
//        this.itemDao = itemDao;
//        this.imageDao = imageDao;
//        this.shopDao = shopDao;
//        this.reviewDao = reviewDao;
//        this.userService = userService;
//        this.rwOrder = rwOrder;
//    }
//
//    @PostConstruct
//    public void init() {
//
//        roles = getRoles();
//        roles.forEach(roleDao::persist);
//
//        countries = getCountries();
//        countries.forEach(countryDao::persist);
//
//        cities = getCities();
//        cities.forEach(cityDao::persist);
//
//        categories = getCategories();
//        categories.forEach(categoryDao::persist);
//
//        users = getUsers();
//        //USER Можно пока одного юзера сделать фискированным чтобы время сэкономить?
//        users.get(0).setUsername("user");
//        users.get(0).setPassword("1");
//        users.get(0).setRoles(null);
//        users.get(0).addRole(roles.stream().filter(r -> r.getName() == "USER").findFirst().orElse(null));
//        //USER Можно пока одного юзера сделать фискированным чтобы время сэкономить?
//        users.forEach(user -> {
//            user.getAddress().forEach(addressDao::persist);
//            user.getImages().forEach(imageDao::persist);
//            userDao.persist(user);
//        });
//
//        shops = getShops();
//        shops.forEach(shop -> {
////            imageDao.persist(shop.getLogo());
//            shopDao.persist(shop);
//        });
//
//        items = getItems();
//        items.forEach(item -> {
////            item.getImages().forEach(imageDao::persist);
//            itemDao.persist(item);
//        });
//
//        reviews = getReviews();
//        reviews.forEach(reviewDao::persist);
//
//        User userAlexander = new User("kooppex@gmail.com", "root228", "root228",
//                "Alexander", "Baranov");
//        Set<Role> adminRole = new HashSet<>();
//        adminRole.add(roleDao.getRoleById(2L));
//        userService.registerNewUser(userAlexander);
//
//        orders = getOrders();
//        orders.forEach((rwOrder::persist));
//
//    }
//
//    private Set<Role> getRoles() {
//        Set<Role> roles = new HashSet<>();
//
//        roles.add(new Role("USER"));
//        roles.add(new Role("ADMIN"));
//
//        return roles;
//    }
//
//    private List<User> getUsers() {
//        String path = "src/main/resources/static/img/users/";
//        List<User> users = new ArrayList<>();
//
//        users.add(getUser("Ivan", "Ivanov", Gender.MALE, path + "0.jpg"));
//        users.add(getUser("Vasily", "Vasiliev", Gender.MALE, path + "1.jpg"));
//        users.add(getUser("Piter", "Petrov", Gender.MALE, path + "2.jpg"));
//        users.add(getUser("Irina", "Irinova", Gender.FEMALE, path + "3.jpg"));
//        users.add(getUser("Sveta", "Svetova", Gender.FEMALE, path + "4.jpg"));
//        users.add(getUser("Alex", "Alexeev", Gender.MALE, path + "5.jpg"));
//        users.add(getUser("Kira", "Kireeva", Gender.FEMALE, path + "6.jpg"));
//        users.add(getUser("Dmitry", "Dmitrov", Gender.MALE, path + "7.jpg"));
//        users.add(getUser("Kiril", "Kirilov", Gender.MALE, path + "8.jpg"));
//        users.add(getUser("Pavel", "Pavlov", Gender.MALE, path + "9.jpg"));
//        users.add(getUser("Konstantin", "Konstantinov", Gender.MALE, path + "9.jpg"));
//        return users;
//    }
//
//    private User getUser(String firstName, String lastName, Gender gender, String imagePath) {
//        User user = new User();
//
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setActivate(true);
//        user.setEmail(user.getFirstName().toLowerCase() + user.getLastName().toLowerCase() + "@mail.com");
//        user.setAge(Integer.parseInt(randomNumberString(2)));
//        user.setUsername(user.getFirstName().toLowerCase() + user.getAge());
//        user.setPassword(randomNumberString(4));
////       user.setBirthday(new GregorianCalendar(1870, Calendar.APRIL,23));
//        user.addAddress(getRandomAddress());
//        user.addAddress(getRandomAddress());
//        user.addAddress(getRandomAddress());
//        user.addAddress(getRandomAddress());
//        user.addAddress(getRandomAddress());
//        user.addRole(randomListElement(new ArrayList<>(roles)));
//        user.setGender(gender);
//        user.addImages(getRandomImage());
//        user.addImages(getRandomImage());
//        user.addImages(getRandomImage());
//        user.addImages(getRandomImage());
//        user.addImages(getRandomImage());
//        user.setPhone(randomPhone());
//        user.addImage(new Image(imagePath, true));
//
//        return user;
//    }
//
//    private List<Category> getCategories() {
//        List<Category> categories = new ArrayList<>();
//
//        categories.add(new Category("Транспорт"));
//        categories.add(new Category("Недвижимость"));
//        categories.add(new Category("Работа"));
//        categories.add(new Category("Услуги"));
//        categories.add(new Category("Личные вещи"));
//        categories.add(new Category("Электроника"));
//        categories.add(new Category("Инструменты"));
//        categories.add(new Category("Животные"));
//        categories.add(new Category("Мебель"));
//
//        return categories;
//    }
//
//    private Address getRandomAddress() {
//        City city = randomListElement(cities);
//
//        return new Address(
//                randomNumberString(6),
//                city.getCountry(),
//                city,
//                "Street " + randomNumberString(3),
//                randomNumberString(2)
//        );
//    }
//
//    private List<Country> getCountries() {
//        List<Country> countries = new ArrayList<>();
//
//        countries.add(new Country("USA"));
//        countries.add(new Country("Russia"));
//        countries.add(new Country("Kazakhstan"));
//        countries.add(new Country("Belarus"));
//        countries.add(new Country("Ukraine"));
//        countries.add(new Country("France"));
//        countries.add(new Country("Spain"));
//
//        return countries;
//    }
//
//    private List<City> getCities() {
//        List<City> cities = new ArrayList<>();
//
//        cities.add(new City("Atlanta", countries.get(0)));
//        cities.add(new City("Boston", countries.get(0)));
//        cities.add(new City("Chicago", countries.get(0)));
//        cities.add(new City("Houston", countries.get(0)));
//        cities.add(new City("Moscow", countries.get(1)));
//        cities.add(new City("Kolomna", countries.get(1)));
//        cities.add(new City("Tula", countries.get(1)));
//        cities.add(new City("Tomsk", countries.get(1)));
//        cities.add(new City("Ekaterinburg", countries.get(1)));
//        cities.add(new City("Nursultan", countries.get(2)));
//        cities.add(new City("Astana", countries.get(2)));
//        cities.add(new City("Turkestan", countries.get(2)));
//        cities.add(new City("Karaganda", countries.get(2)));
//        cities.add(new City("Minsk", countries.get(3)));
//        cities.add(new City("Brest", countries.get(3)));
//        cities.add(new City("Gomel", countries.get(3)));
//        cities.add(new City("Vitebsk", countries.get(3)));
//        cities.add(new City("Kiev", countries.get(4)));
//        cities.add(new City("Odessa", countries.get(4)));
//        cities.add(new City("Donetsk", countries.get(4)));
//        cities.add(new City("Paris", countries.get(5)));
//        cities.add(new City("Lion", countries.get(5)));
//        cities.add(new City("Marcel", countries.get(5)));
//        cities.add(new City("Barcelona", countries.get(6)));
//        cities.add(new City("Madrid", countries.get(6)));
//
//        return cities;
//    }
//
//    private List<Shop> getShops() {
//        String path = "src/main/resources/static/img/shops/";
//
//        List<Shop> shops = new ArrayList<>();
//        shops.add(getShop("M-Group", "Мебель для дома и сада", path + "mgroup.jpg"));
//        shops.add(getShop("Vesco", "Элитная загородная, городская и коммерческая недвижимость", path + "vesco.jpg"));
//        shops.add(getShop("BigTV", "Проверенная временем цифровые устройства", path + "bigtv.jpg"));
//        shops.add(getShop("MskServer", "Постройте свой бизнес без переплат", path + "mskserver.jpg"));
//        shops.add(getShop("SereverGate", "Восстановленное IT оборудование", path + "servergate.jpg"));
//        shops.add(getShop("iBatt", "Аккумуляторные батареи для ноутбуков, блоки питания для ноутбуков", path + "ibatt.jpg"));
//        shops.add(getShop("FotoCCCP", "Фототехника и оптика. Переходники, адаптеры и аксессуары для фото", path + "fotocccp.jpg"));
//        shops.add(getShop("RedKey", "Сеть салонов Hi-Fi техники", path + "redkey.jpg"));
//        shops.add(getShop("SuperStar", "Сеть бутиков высокой моды", path + "cl.jpg"));
//
//        return shops;
//    }
//
//    private Shop getShop(String name, String description, String logoUrl) {
//        Shop shop = new Shop();
//
//        shop.setName(name);
//        shop.setDescription(description);
//        shop.setLogo(new Image(logoUrl));
//        shop.setEmail(shop.getName().toLowerCase() + "@mail.com");
//        shop.setPhone(randomPhone());
//        shop.setRating(randomRating());
//        shop.setLocation(randomListElement(countries));
//        shop.setCity(randomListElement(cities));
//        shop.setUser(randomListElement(users));
//
//        return shop;
//    }
//
//    private List<Item> getItems() {
//        String path = "src/main/resources/static/img/items/";
//
//        Item item1 = new Item();
//        item1.setName("Harman kardon mini");
//        item1.setPrice(BigDecimal.valueOf(500));
//        item1.addCategory(categories.get(5));
//        item1.setDescription("Состояние на четверочку. На большой громкости начинает хрипеть, на умеренной можно слушать. В комплекте aux кабель. Продаю как есть, в качестве запчасти, без гарантий, проверка на месте.");
//        item1.setRating(randomRating());
//        item1.addImage(new Image(path + "carbon/1.jpg"));
//        item1.addImage(new Image(path + "carbon/2.jpg"));
//        item1.addImage(new Image(path + "carbon/3.jpg"));
//        item1.setShop(randomListElement(shops));
//        item1.setCount(Integer.parseInt(randomNumberString(1)) + 1);
//
//        Item item2 = new Item();
//        item2.setName("iPhone 4 16gb");
//        item2.setPrice(BigDecimal.valueOf(1000));
//        item2.addCategory(categories.get(5));
//        item2.setDescription("Эта модель давно не поддерживается Apple, поэтому из приложений кроме телеграмм ничего поставить не удастся. Кабель usb в комплекте. Продаю как есть, без гарантий, так как давно им не пользовался, сколько держит батарею без понятия. Любые проверки на месте.");
//        item2.setRating(randomRating());
//        item2.addImage(new Image(path + "iphone4/1.jpg"));
//        item2.addImage(new Image(path + "iphone4/2.jpg"));
//        item2.setShop(randomListElement(shops));
//        item2.setCount(Integer.parseInt(randomNumberString(1)) + 1);
//
//        Item item3 = new Item();
//        item3.setName("Зеркало правое Renault Logan 1");
//        item3.setPrice(BigDecimal.valueOf(600));
//        item3.addCategory(categories.get(0));
//        item3.setDescription("Механическое правое зеркало на первый Логан. На зеркальном элементе трещины. Снято с собственного автомобиля.");
//        item3.setRating(randomRating());
//        item3.addImage(new Image(path + "mirror/1.jpg"));
//        item3.addImage(new Image(path + "mirror/2.jpg"));
//        item3.setShop(randomListElement(shops));
//        item3.setCount(Integer.parseInt(randomNumberString(1)) + 1);
//
//        Item item4 = new Item();
//        item4.setName("Дом каркасно щитовой");
//        item4.setPrice(BigDecimal.valueOf(590000));
//        item4.addCategory(categories.get(1));
//        item4.setDescription("Деревянный каркасный дом 6х8 для полноценного проживания с окнами ПВХ, двери ДФ, отделка внутри и снаружи евровагонка камерной сушки");
//        item4.setRating(randomRating());
//        item4.addImage(new Image(path + "house/1.jpg"));
//        item4.setShop(randomListElement(shops));
//        item4.setCount(Integer.parseInt(randomNumberString(1)) + 1);
//
//        Item item5 = new Item();
//        item5.setName("Котенок Курильский бобтейл");
//        item5.setPrice(BigDecimal.valueOf(25000));
//        item5.addCategory(categories.get(7));
//        item5.setDescription("Очень ласковый и невероятно мурчащий котёнок курильского бобтейла");
//        item5.setRating(randomRating());
//        item5.addImage(new Image(path + "cat/1.jpg"));
//        item5.addImage(new Image(path + "cat/2.jpg"));
//        item5.setShop(randomListElement(shops));
//        item5.setCount(Integer.parseInt(randomNumberString(1)) + 1);
//
//        Item item6 = new Item();
//        item6.setName("Стол обеденный");
//        item6.setPrice(BigDecimal.valueOf(17750));
//        item6.addCategory(categories.get(8));
//        item6.setDescription("Стоимость указана за обеденный стол в базовой комплектации без сборки. У нас вы можете купить обеденный стол и получить стулья в подарок.");
//        item6.setRating(randomRating());
//        item6.addImage(new Image(path + "table/1.jpg"));
//        item6.setShop(randomListElement(shops));
//        item6.setCount(Integer.parseInt(randomNumberString(1)) + 1);
//
//        Item item7 = new Item();
//        item7.setName("Новый 7");
//        item7.setPrice(BigDecimal.valueOf(5000000));
//        item7.addCategory(categories.get(8));
//        item7.setDescription("Новый 7 товар не в магазине.----------------------------------------------");
//        item7.setRating(randomRating());
//        item7.addImage(new Image(path + "btc/btc.jpeg"));
//        item7.setShop(null);
//        item7.setCount(null);
//        Item item8 = new Item();
//        item8.setName("Новый 8");
//        item8.setPrice(BigDecimal.valueOf(5000000));
//        item8.addCategory(categories.get(8));
//        item8.setDescription("Новый 7 товар не в магазине.----------------------------------------------");
//        item8.setRating(randomRating());
//        item8.addImage(new Image(path + "btc/btc.jpeg"));
//        item8.setShop(null);
//        item8.setCount(null);
//
//
//
//        List<Item> items = new ArrayList<>();
//        items.add(item1);
//        items.add(item2);
//        items.add(item3);
//        items.add(item4);
//        items.add(item5);
//        items.add(item6);
//        items.add(item7);
//        items.add(item8);
//
//        return items;
//    }
//
//    private List<Review> getReviews() {
//        List<Review> reviews = new ArrayList<>();
//
//        reviews.add(getReview(
//                "Хорошие таблетки, посуда чистая",
//                "Единственное, когда открываешь упаковку с таблеткой, красный шарик выпадает.",
//                "Запах очень концентрированный, но аллергии нет."
//        ));
//        reviews.add(getReview(
//                "Хорошо отмывает, нет запаха",
//                "нет.",
//                "Отличные таблетки, пользуюсь давно, довольна"
//        ));
//        reviews.add(getReview(
//                "Цена ",
//                "Плохо отмывает .",
//                "Сравнивал с другой маркой - отмывает заметно хуже "
//        ));
//        reviews.add(getReview(
//                "Качество на 5+ ",
//                "Нет ",
//                "Спасибо ozon за скидки,такие таблетки в розничных магазинах стоят в 2-3 раза дороже "
//        ));
//        reviews.add(getReview(
//                "Компактный, тихий, яркий дизайн, быстро греет, шнур средней длинны, поворот на греющей платформе на 360",
//                "Не обнаружено и надеюсь не будет  ",
//                "Прекрасный вариант для работы. Преобрести удалось с хорошей скидкой. Нагревается быстро. Работает не громко. Запах пластика отсутствует. Закипает вода на 1 литр за 3 минуты "
//        ));
//        reviews.add(getReview(
//                "легкий, при нагреве тихий, после первых двух кипячений посторонние запахи улетучились. Очень доволен. ",
//                "не выявлено",
//                "Сенсор не подвел, соотношение цена-качество на отличном уровне"
//        ));
//        reviews.add(getReview(
//                "компактный, удобно, веселый цвет",
//                "пока не выявлено ",
//                "Удачная компактная конструкция небольшого объема, закипание быстрое, несмотря на невысокую мощность. Веселые расцветки на любой вкус. Фильтрующий элемент в носике, крышка открывается полностью, заливать воду и мыть удобно. Покупкой доволен "
//        ));
//        reviews.add(getReview(
//                "Маленький, яркий, экономичный в расходе электричества",
//                "Запах пластика есть, но это было ожидаемо",
//                "Нужен был именно небольшой чайник, а главное со средней мощностью, потому что прежний регулярно вышибал нам предохранитель. Из всех вариантов выбрала этот и пока не жалею. "
//        ));
//        reviews.add(getReview(
//                "красивый  ",
//                "немного пахнет пластиком  ",
//                "после кипячения остался запах пластика, но думаю со временем пройдет. а так в целом красивый, небольшой чайничек. купили в поездку  "
//        ));
//        reviews.add(getReview(
//                "Очень удобный размер чайника. Не большой, компактный. Удобен для работы и можно взять с собой в поездку.",
//                "пока не обнаружила",
//                "Первоначально был запах пластика. Но после мытья и 1 кипячения воды, запах исчез. Чайник работает довольно таки тихо. 1 литр воды кипятит примерно 5-6 минут."
//        ));
//        reviews.add(getReview(
//                "Нормальная мышь ",
//                "Неустойчива в горизонтальном положении",
//                "Мышь нормальная, темболее за свои деньги. Один только минус у нее, на поверхности у этой мыши 5 точек соприкосновения с поверхностью, причем одна из них в середине с одной стороны,"
//        ));
//        reviews.add(getReview(
//                "Качественная мышь. Удобная. Полноразмерная. Легко менять батарейки. Есть выключатель. ",
//                "Не обнаружено ",
//                "18 месецев на одной батарейке, посмотрим.."
//        ));
//        reviews.add(getReview(
//                "мышь полноразмерная, поэтому очень удобно лежит в руке, приятный на ощупь пластик",
//                "из мелких: достаточно громкие щелчки. ну как громкие, скорее, обычные.",
//                "почему-то не считывает вращение колёсико, если его крутить медленно."
//        ));
//        reviews.add(getReview(
//                "Отличная мышка за свои деньги. ",
//                "Пока очень нравится, недостатков не выявили",
//                "Мышь удобно лежит в руке, работает отлично без нареканий, удобно кликать и открывать документы.  "
//        ));
//        reviews.add(getReview(
//                "Цена, качество, удобство в использовании  ",
//                "недостатков не выявила  ",
//                "Я уже на протяжении долгих лет пользуюсь мышками исключительно от логитек. "
//        ));
//
//        return reviews;
//    }
//
//    private Review getReview(String dignity, String flaw, String comment) {
//        return new Review(dignity, flaw, comment,
//                new Date(),
//                new Random().nextInt(5) + 1,
//                randomListElement(items),
//                randomListElement(users),
//                randomListElement(shops)
//        );
//    }
//
//    /* Utilities */
//
//    private String randomNumberString(int length) {
//        StringBuilder sb = new StringBuilder();
//        for(int i = 0; i < length; i++) {
//            sb.append((int)(Math.random()*10));
//        }
//        return sb.toString();
//    }
//
//    private <T> T randomListElement(List<T> list) {
//        int randomIndex = new Random().nextInt(list.size());
//        return list.get(randomIndex);
//    }
//
//    private Double randomRating() {
//        return Math.random()*5;
//    }
//
//    private Image getRandomImage() {
//        Image image = new Image();
//        return image.ImageFromURL("https://thispersondoesnotexist.com/image");
//    }
//
//    private String randomPhone() {
//        return new StringBuilder().append("(").append(randomNumberString(3)).append(") ")
//                .append(randomNumberString(3)).append("-")
//                .append(randomNumberString(2)).append("-")
//                .append(randomNumberString(2)).toString();
//    }
//
//    private static Status generateRandomStatus() {
//        Status[] values = Status.values();
//        int length = values.length;
//        int randIndex = new Random().nextInt(length);
//        return values[randIndex];
//    }
//
//    private Order getOrder() {
//        Order order = new Order();
//
//        order.setItems(Arrays.asList(randomListElement(items), randomListElement(items), randomListElement(items)));
//        order.setDate(new GregorianCalendar());
//        order.setStatus(generateRandomStatus());
//        order.setAddress(null);
//        order.setOrderDetails(null);
////        order.setAddress(users.get(0).getAddress().get(0));
//        order.setTotal(new BigDecimal("1000.0"));
//        order.setUser(users.get(0));
//        order.setBuyerName("Buyer from" + getRandomAddress().getCountry());
//        order.setBuyerPhone("100");
//
//        return order;
//    }
//
//    private List<Order> getOrders() {
//        List<Order> orders = new ArrayList<>();
//        orders.add(getOrder());
//        orders.add(getOrder());
//        orders.add(getOrder());
//        orders.add(getOrder());
//        orders.add(getOrder());
//
//        return orders;
//    }
//}
