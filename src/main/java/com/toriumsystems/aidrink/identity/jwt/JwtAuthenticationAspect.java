package com.toriumsystems.aidrink.identity.jwt;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.toriumsystems.aidrink.identity.annotations.IdentityId;
import com.toriumsystems.aidrink.identity.model.JwtAuthenticationToken;
import com.toriumsystems.aidrink.identity.model.UserIdentityDetails;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Aspect
@Component
@AllArgsConstructor
public class JwtAuthenticationAspect {

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object retrieveIdentityId(ProceedingJoinPoint joinPoint) throws Throwable {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            String route = request.getRequestURI();

            if (!route.equals("/api/v1/auth/login")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid authentication gateway. Please use dedicated authentication resource");
            }

            return joinPoint.proceed();
        }

        if (auth instanceof JwtAuthenticationToken) {
            Long identityId = ((UserIdentityDetails) auth.getPrincipal()).getIdentity().getId();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(IdentityId.class)) {
                    joinPoint.getArgs()[i] = identityId;
                    break;
                }
            }
            return joinPoint.proceed(joinPoint.getArgs());
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED);
    }

}
