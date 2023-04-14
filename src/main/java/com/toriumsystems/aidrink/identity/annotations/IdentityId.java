package com.toriumsystems.aidrink.identity.annotations;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IdentityId {
}