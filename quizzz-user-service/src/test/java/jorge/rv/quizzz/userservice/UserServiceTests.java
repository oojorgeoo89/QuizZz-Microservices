package jorge.rv.quizzz.userservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import jorge.rv.quizzz.userservice.exceptions.QuizZzException;
import jorge.rv.quizzz.userservice.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.userservice.exceptions.UnauthorizedActionException;
import jorge.rv.quizzz.userservice.exceptions.UserAlreadyExistsException;
import jorge.rv.quizzz.userservice.model.User;
import jorge.rv.quizzz.userservice.repository.UserRepository;
import jorge.rv.quizzz.userservice.service.UserService;
import jorge.rv.quizzz.userservice.service.UserServiceImpl;

public class UserServiceTests {

	UserService service;

	// Mocks
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;

	User user = new User();

	@Before
	public void before() {
		userRepository = mock(UserRepository.class);
		passwordEncoder = mock(PasswordEncoder.class);

		service = new UserServiceImpl(userRepository, passwordEncoder);

		user.setEmail("a@a.com");
		user.setPassword("Password");
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void findUserByUsername_shouldntFind() {
		when(userRepository.findByEmail(user.getEmail())).thenThrow(new ResourceUnavailableException("test"));

		service.findByUsernameOrEmail("test");
	}


	@Test
	public void findUserByUsername_shouldFindByEmail() {
		when(userRepository.findByUsername(user.getEmail())).thenThrow(new ResourceUnavailableException("test"));
		when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

		User localUser = service.findByUsernameOrEmail(user.getEmail());

		verify(userRepository, times(1)).findByEmail(user.getEmail());
		assertNotNull(localUser);
	}
	
	@Test
	public void findUserByUsername_shouldFindByName() {
		when(userRepository.findByEmail(user.getUsername())).thenThrow(new ResourceUnavailableException("test"));
		when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

		User localUser = service.findByUsernameOrEmail(user.getUsername());

		verify(userRepository, times(1)).findByUsername(user.getUsername());
		assertNotNull(localUser);
	}


	@Test
	public void saveNewUserShouldSucceed() throws UserAlreadyExistsException {
		when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
		when(userRepository.save(user)).thenReturn(user);

		User returned = service.saveUser(user);

		verify(userRepository, times(1)).save(user);
		assertTrue(user.equals(returned));
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void saveNewUserMailExistsShouldFail() throws UserAlreadyExistsException {
		when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);

		service.saveUser(user);
	}

	@Test
	public void deleteUser() throws UnauthorizedActionException, ResourceUnavailableException {
		when(userRepository.findOne(user.getId())).thenReturn(user);
		service.delete(user.getId());

		verify(userRepository, times(1)).delete(user);
	}

	@Test(expected = ResourceUnavailableException.class)
	public void testDeleteUnexistentUser() throws QuizZzException {
		when(userRepository.findOne(user.getId())).thenReturn(null);

		service.delete(user.getId());
	}

	@Test(expected = UnauthorizedActionException.class)
	public void testDeleteFromWrongUser() throws QuizZzException {
		when(userRepository.findOne(user.getId())).thenReturn(user);
		doThrow(new UnauthorizedActionException()).when(userRepository).delete(user);

		service.delete(user.getId());
	}

	@Test
	public void updatePasswordShouldEncrypt() {
		final String clearPass = "clearPassword";
		final String encodedPass = "encodedPassword";
		when(passwordEncoder.encode(clearPass)).thenReturn(encodedPass);
		when(userRepository.save(user)).thenReturn(user);

		User newUser = service.updatePassword(user, clearPass);

		verify(passwordEncoder, times(1)).encode(clearPass);
		verify(userRepository, times(1)).save(user);
		assertEquals(encodedPass, newUser.getPassword());
	}
}
