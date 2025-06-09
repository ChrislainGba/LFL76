package com.lfl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Lfl76ApplicationTests {

	@Test
	void contextLoads() {
	}

}

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User(1L, "Alice", "alice@example.com");
    }

    @Test
    void shouldCreateUser() {
        // given
        when(userRepository.save(user)).thenReturn(user);

        // when
        User result = userService.createUser(user);

        // then
        assertEquals("Alice", result.getName());
        verify(userRepository).save(user);
    }

    @Test
    void shouldGetUserById() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        User result = userService.getUserById(1L);

        // then
        assertEquals("Alice", result.getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldUpdateUser() {
        // given
        User updatedUser = new User(1L, "Alice Updated", "alice@new.com");
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // when
        User result = userService.updateUser(updatedUser);

        // then
        assertEquals("Alice Updated", result.getName());
        verify(userRepository).save(updatedUser);
    }

    @Test
    void shouldDeleteUser() {
        // given - nothing

        // when
        userService.deleteUser(1L);

        // then
        verify(userRepository).deleteById(1L);
    }

    @Test
    void shouldGetAllUsers() {
        // given
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        // when
        List<User> result = userService.getAllUsers();

        // then
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }
}


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User(1L, "Alice", "alice@example.com");
    }

    @Test
    void shouldShowUserList() throws Exception {
        // given
        when(userService.getAllUsers()).thenReturn(List.of(user));

        // when + then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void shouldShowCreateForm() throws Exception {
        // given - nothing

        // when + then
        mockMvc.perform(get("/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-form"));
    }

    @Test
    void shouldSaveUserAndRedirect() throws Exception {
        // given
        when(userService.createUser(any(User.class))).thenReturn(user);

        // when + then
        mockMvc.perform(post("/users/save")
                .param("name", "Alice")
                .param("email", "alice@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void shouldShowEditForm() throws Exception {
        // given
        when(userService.getUserById(1L)).thenReturn(user);

        // when + then
        mockMvc.perform(get("/users/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-form"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        // given
        when(userService.updateUser(any(User.class))).thenReturn(user);

        // when + then
        mockMvc.perform(post("/users/update")
                .param("id", "1")
                .param("name", "Updated Name")
                .param("email", "updated@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        // given - nothing

        // when + then
        mockMvc.perform(get("/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService).deleteUser(1L);
    }
}



