package water.api;

/**
 * REST API registration endpoint.
 *
 * The interface should be overriden by clients which would like
 * to provide additional REST API endpoints.
 *
 * The registration is divided into two parts:
 *   - register Handlers to expose a new REST API endpoint (e.g., /3/ModelBuilder/XGBoost/)
 *   - register Schemas to provide a new definition of REST API input/output
 */
public interface RegisterRestApi {

  /**
   * TODO: would be better to pass Jetty object to register `jetty.register(new ApiHandler())
   * @param relativeResourcePath
   *
   * @deprecated use {@link #registerEndPoints(RestApiContext)}
   */
  @Deprecated
  void register(String relativeResourcePath);

  /**
   *
   * @param context
   */
  void registerEndPoints(RestApiContext context);

  /**
   * 
   * @param context
   */
  void registerSchemas(RestApiContext context);
}

interface RestApiContext {
  
  Route registerEndpoint(String api_name,
                         String method_uri,
                         Class<? extends Handler> handler_class,
                         String handler_method,
                         String summary);

  Route registerEndpoint(String api_name,
                         String http_method,
                         String url,
                         Class<? extends Handler> handler_class,
                         String handler_method,
                         String summary,
                         HandlerFactory handler_factory);


  Route registerEndpoint(String method_uri,
                         Class<? extends RestApiHandler> handler_clz);

  void registerSchema(Schema ... schemas);
}
