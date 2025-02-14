1.AuthorizationServerConfig Class
  passwordEncoder(): Defines a PasswordEncoder bean using BCryptPasswordEncoder.
  authServerSecurityFilterChain(HttpSecurity http): Configures security filter chain for the authorization server.
  registeredClientRepository(): Registers OAuth2 clients with in-memory storage.
  jwkSource(): Provides JSON Web Key (JWK) source for JWT signing.
  authorizationServerSettings(): Configures authorization server settings.
2. DefaultSecurityConfig Class
  defaultSecurityFilterChain(HttpSecurity http): Configures security filter chain for default security settings.
  bindAuthenticationProvider(AuthenticationManagerBuilder auth): Binds custom authentication provider.
3. User Entity
  @Entity: Marks the class as a JPA entity.
  @Table(name = "User1"): Specifies the table name.
  @Id, @GeneratedValue(strategy = GenerationType.UUID): Defines the primary key and its generation strategy.
4. UserRepository Interface
  @Repository: Marks the interface as a Spring Data repository.
5. CustomerUserDetailsService Class
  loadUserByUsername(String email): Loads user details by email.
  getAuthorities(List<String> roles): Converts roles to granted authorities.
6. WebClientConfiguration Class
  webClient(OAuth2AuthorizedClientManager authorizedClientManager): Configures WebClient with OAuth2 support.
  authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository): Configures OAuth2 authorized client manager.
7. WebSecurityConfig Class
  securityFilterChain(HttpSecurity http): Configures security filter chain with authorization rules, OAuth2 resource server, form login, and logout settings.
8. application.yml File
  server.port: Sets the server port.
  spring.datasource: Configures database connection.
  spring.jpa: Configures JPA settings.
  spring.security.oauth2: Configures OAuth2 client and resource server settings.
9. ResourceServerConfig Class
  securityFilterChain(HttpSecurity http): Configures security filter chain for the resource server, securing /api/** endpoints with JWT.
10. UserController Class
  getUser(): Returns a list of users.
11. HelloController Class
  hello(): Returns a greeting message.
  users(OAuth2AuthorizedClient client): Fetches users from the resource server using WebClient.
12. RegistrationController Class
  registerUser(UserModel userModel, HttpServletRequest request): Registers a new user and publishes a registration event.
  verifyRegistration(String token): Verifies user registration.
  resendVerificationToken(String oldToken, HttpServletRequest request): Resends verification token.
  resetPassword(PasswordModel passwordModel, HttpServletRequest request): Initiates password reset process.
  savePassword(String token, PasswordModel passwordModel): Saves the new password.
  changePassword(PasswordModel passwordModel): Changes the user's password.
13. PasswordResetToken Entity
  @Entity: Marks the class as a JPA entity.
  @Id, @GeneratedValue(strategy = GenerationType.UUID): Defines the primary key and its generation strategy.
  @OneToOne: Defines a one-to-one relationship with the User entity.
14. VerificationToken Entity
  @Entity: Marks the class as a JPA entity.
  @Id, @GeneratedValue(strategy = GenerationType.UUID): Defines the primary key and its generation strategy.
  @OneToOne: Defines a one-to-one relationship with the User entity.
15. RegistrationCompleteEvent Class
  @Getter, @Setter: Lombok annotations for generating getters and setters.
  @ApplicationEvent: Marks the class as an application event.
16. RegistrationCompleteEventListener Class
  @Component: Marks the class as a Spring component.
  @Slf4j: Lombok annotation for logging.
17. PasswordModel Class
  @Data: Lombok annotation for generating getters, setters, and other utility methods.
18. UserModel Class
  @Data, @AllArgsConstructor, @NoArgsConstructor: Lombok annotations for generating constructors and utility methods.
19. PasswordResetTokenRepository Interface
  @Repository: Marks the interface as a Spring Data repository.
20. VerificationTokenRepository Interface
@Repository: Marks the interface as a Spring Data repository.
21. UserService Interface
  createPasswordResetTokenForUser(User user, String token): Creates a password reset token for a user.
  registerUser(UserModel userModel): Registers a new user.
  saveVerificationTokenForUser(String token, User user): Saves a verification token for a user.
  validateVerificationToken(String token): Validates a verification token.
  generateNewVerificationToken(String oldToken): Generates a new verification token.
  findUserByEmail(String email): Finds a user by email.
  validatePasswordResetToken(String token): Validates a password reset token.
  getUserByPasswordResetToken(String token): Retrieves a user by password reset token.
  changePassword(User user, String newPassword): Changes a user's password.
  checkIfValidOldPassword(User user, String oldPassword): Checks if the old password is valid.
22. UserServiceImpl Class
  Implements all methods defined in the UserService interface.
