package com.gamealoon.cors;

import java.lang.annotation.*;
import play.mvc.*;
import play.mvc.Http.*;

/**
 * Implementing CORS for gamealoon.http://daniel.reuterwall.com/blog/2013/04/15/play-with-cors/
 * 
 * @author Partho
 *
 */
public class CorsComposition {

    /**
     * Wraps the annotated action in an <code>CorsAction</code>.
     */
    @With(CorsAction.class)
    @Target({ ElementType.TYPE, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Cors {
        String value() default "*";
    }

    public static class CorsAction extends Action<Cors> {
        public Result call(Context context) throws Throwable{
            Response response = context.response();
            response.setHeader("Access-Control-Allow-Origin",configuration.value());

            //Handle preflight requests
            if(context.request().method().equals("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Methods", "POST");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

                return ok();
            }

            response.setHeader("Access-Control-Allow-Headers","X-Requested-With");
            return delegate.call(context);
        }
    }
}
