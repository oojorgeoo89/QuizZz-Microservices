package jorge.rv.quizzz.auth.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jorge.rv.quizzz.auth.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.auth.model.User;
import jorge.rv.quizzz.auth.repository.UserRepository;
import jorge.rv.quizzz.auth.service.UserService;
import jorge.rv.quizzz.auth.service.UserServiceImpl;

public class UserServiceTests {

	UserService service;

	// Mocks
	UserRepository userRepository;

	User user = new User();

	@Before
	public void before() {
		userRepository = mock(UserRepository.class);

		service = new UserServiceImpl(userRepository);

		user.setUsername("user1");
		user.setEmail("a@a.com");
		user.setPassword("Password");
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void findUserByUsername_shouldntFind() {
		when(userRepository.findByEmail(user.getEmail())).thenThrow(new ResourceUnavailableException("test"));

		service.loadUserByUsername("test");
	}


	@Test
	public void findUserByUsername_shouldFindByEmail() {
		when(userRepository.findByUsername(user.getEmail())).thenThrow(new ResourceUnavailableException("test"));
		when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

		UserDetails localUser = service.loadUserByUsername(user.getEmail());

		verify(userRepository, times(1)).findByEmail(user.getEmail());
		assertNotNull(localUser);
	}
	
	@Test
	public void findUserByUsername_shouldFindByName() {
		when(userRepository.findByEmail(user.getUsername())).thenThrow(new ResourceUnavailableException("test"));
		when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

		UserDetails localUser = service.loadUserByUsername(user.getUsername());

		verify(userRepository, times(1)).findByUsername(user.getUsername());
		assertNotNull(localUser);
	}

}
