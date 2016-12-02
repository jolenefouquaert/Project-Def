package org.ibcn.gso.utils.entitysystemframework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate fields of managed instances (e.g. EntitySystem implementations) with
 * Inject to allow the IoC container to automatically set matching objects when
 * available.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
}
