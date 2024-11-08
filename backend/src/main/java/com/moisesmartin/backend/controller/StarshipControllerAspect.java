package com.moisesmartin.backend.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StarshipControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(StarshipControllerAspect.class);

    @Before("execution(* com.moisesmartin.backend.controller.StarshipController.findById(..)) && args(id)")
    public void logNegativeId(JoinPoint joinPoint, Long id) {
        if (id < 0) {
            logger.warn("Requested starship with negative ID: {}", id);
        }
    }
}