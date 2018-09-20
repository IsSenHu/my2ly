package com.husen.jian.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: 胡森
 * @Description: spring security 配置，项目启动的时候自动配置好已设置的权限
 * @Date: Created in 0:54 2018/5/4
 * @Modified By: 胡森
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final UserService userService;
    /**
     * 登录页面
     */
    private static final String LOGIN = "/login";
    /**
     * 认证接口
     */
    private static final String AUTHENTICATE = "/authenticate";
    /**
     * 主页
     */
    private static final String INDEX = "/index";
    /**
     * 静态资源
     */
    private static final String BOWER_COMPONENTS = "/bower_components/**";
    private static final String BUILD = "/build/**";
    private static final String CSS = "/css/**";
    private static final String DIST = "/dist/**";
    private static final String FONT = "/font/**";
    private static final String JS = "/js/**";
    private static final String PLUGINS = "/plugins/**";
    private static final String FAVICON = "/favicon.ico";

    /**
     * cookie 私钥
     */
    private static final String KEY = "mybatis-cookie-private-key";

    /**默认启用*/
    private static final Integer ENABLE = 1;

//    private final IdService idService;
//    private final DataSource dataSource;
//    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
//    @Autowired
//    public WebSecurityConfig(UserService userService, IdService idService, DataSource dataSource) {
//        this.userService = userService;
//        this.idService = idService;
//        this.dataSource = dataSource;
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

//    private PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        //配置数据源
//        jdbcTokenRepository.setDataSource(dataSource);
//        //第一次启动的时候自动建表(可以不用这句话，自己手动建表，源码中有语句的)
//        jdbcTokenRepository.setCreateTableOnStartup(true);
//        return jdbcTokenRepository;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        log.info("权限初始化...");
//        List<MenuPo> allUrls = userService.allUrls();
//        log.info("总共有{}个权限", allUrls.size());
//        //初始化授权菜单集合
//        initAuthorizeMenus(allUrls);
                //添加前置拦截器
//                .addFilterBefore((MyPermissionReloadFilter)(servletRequest, servletResponse, filterChain) -> {
//                    HttpSession session = ((HttpServletRequest)servletRequest).getSession();
//                    SecurityContext securityContext;
//                    Authentication authentication;
//                    UserPo principal;
//                    if(!Objects.isNull(session) && !Objects.isNull((securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY)))
//                            && !Objects.isNull((authentication = securityContext.getAuthentication())) && !Objects.isNull((principal = (UserPo) authentication.getPrincipal()))) {
//                        //用户权限是否已被修改过
//                        Boolean ifModified = WebSecurityConstant.USER_PERMISSION_IF_MODIFIED_MAP.get(principal.getUserId());
//                        log.info("[{}]用户权限相关的数据被修改过[{}]", principal.getUserId(), ifModified);
//                        if(!Objects.isNull(ifModified) && ifModified) {
//                            //如果修改过，则重新加载用户的权限
//                            userService.initAuthorities(principal, securityContext, authentication);
//                        }
//                    }
//                    filterChain.doFilter(servletRequest, servletResponse);
//                }, SecurityContextPersistenceFilter.class);

        http
                .csrf().disable()
                .authorizeRequests()
                //允许所有用户访问 登录页面和进行验证的连接
                .antMatchers("/**").permitAll();
//                //其他地址的访问均需要验证权限
//                .anyRequest().authenticated()
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
//                        //权限设置拦截器
//                        fsi.setSecurityMetadataSource((MyFilterInvocationSecurityMetadataSource)(object) ->{
//                            FilterInvocation fi = (FilterInvocation) object;
//                            String url = fi.getRequestUrl();
//                            for(Map.Entry<String, String> entry : WebSecurityConstant.AUTHORIZE_MENUS.entrySet()) {
//                                if (antPathMatcher.match(entry.getKey(), url)) {
//                                    return SecurityConfig.createList(entry.getValue());
//                                }
//                            }
//                            return null;
//                        });
//                        //访问决策拦截器
//                        fsi.setAccessDecisionManager((MyAccessDecisionManager)(authentication, object, configAttributes) -> {
//                            for (ConfigAttribute ca : configAttributes) {
//                                String needMenuId = ca.getAttribute();
//                                for (GrantedAuthority ga : authentication.getAuthorities()) {
//                                    log.info("权限为:[{}]", ga.getAuthority());
//                                    if (ga.getAuthority().equals(needMenuId)) {
//                                        //匹配到有对应角色,则允许通过
//                                        return;
//                                    }
//                                }
//                            }
//                            //该url有配置权限,但是当然登录用户没有匹配到对应权限,则禁止访问
//                            throw new AccessDeniedException("not allow");
//                        });
//                        return fsi;
//                    }
//                })
//                .and()
//                //指定登录页是
//                .formLogin().loginPage(LOGIN)
//                //登录成功后默认跳转的页面
//                .defaultSuccessUrl(INDEX)
//                .and()
//                //开启cookie保存用户数据
//                .rememberMe()
//                //设置token Repository
//                .tokenRepository(persistentTokenRepository())
//                //配置token过期时间
//                .tokenValiditySeconds(60 * 60 * 24 * 7)
//                //设置cookie私钥
//                .key(KEY)
//                .and()
//                //登出成功后默认跳转到登录页面
//                .logout().logoutSuccessUrl(LOGIN).permitAll();
    }

//    /**
//     * 初始化url权限集合
//     * @param allUrls 所有的Url
//     */
//    private void initAuthorizeMenus(List<MenuPo> allUrls) {
//        Map<String, String> authorizeMenus = WebSecurityConstant.AUTHORIZE_MENUS;
//        if(CollectionUtils.isNotEmpty(allUrls)) {
//            allUrls.forEach(menu -> {
//                if(!Objects.isNull(menu)) {
//                    authorizeMenus.put(menu.getUrl(), NumberUtils.compare(ENABLE, menu.getIsEnable()) == 0 ? String.valueOf(menu.getMenuId()) : String.valueOf(idService.getId()));
//                }
//            });
//        }
//    }
//
//    @Override
//    public void configure(WebSecurity web) {
//        //不去拦截这些静态资源
//        web.ignoring().antMatchers(BOWER_COMPONENTS, BUILD, CSS, DIST, FONT, JS, PLUGINS, FAVICON);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @FunctionalInterface
//    interface MyFilterInvocationSecurityMetadataSource extends FilterInvocationSecurityMetadataSource {
//        @Override
//        Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException;
//
//        @Override
//        default Collection<ConfigAttribute> getAllConfigAttributes() {
//            return null;
//        }
//
//        @Override
//        default boolean supports(Class<?> clazz) {
//            return FilterInvocation.class.isAssignableFrom(clazz);
//        }
//    }
//
//    @FunctionalInterface
//    interface MyAccessDecisionManager extends AccessDecisionManager {
//        @Override
//        void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException;
//
//        @Override
//        default boolean supports(ConfigAttribute attribute) {
//            return true;
//        }
//
//        @Override
//        default boolean supports(Class<?> clazz) {
//            return true;
//        }
//    }
//
//    @FunctionalInterface
//    interface MyPermissionReloadFilter extends Filter {
//        @Override
//        default void init(FilterConfig filterConfig) {
//            log.info("权限更新过滤器初始化中...");
//        }
//
//        @Override
//        void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException;
//
//        @Override
//        default void destroy() {
//            log.info("权限更新过滤器销毁中....");
//        }
//    }
}
