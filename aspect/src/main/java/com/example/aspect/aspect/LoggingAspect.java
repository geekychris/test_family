
package com.example.aspect.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut that matches all methods in the service package
     */
    @Pointcut("execution(* com.example.aspect.service.*.*(..))")
    public void serviceMethodExecution() {
    }

    /**
     * Before advice: Executes before the matched method execution
     */
    @Before("serviceMethodExecution()")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        


        logger.info(">> Entering method: {}.{} with arguments: {}",
                className, methodName, Arrays.toString(args));
        System.out.println("BEFORE: Entering " + className + "." + methodName + 
                " with arguments: " + Arrays.toString(args));
    }

    /**
     * After advice: Executes after the matched method execution (regardless of outcome)
     */
    @After("serviceMethodExecution()")
    public void logAfterMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.info("<< Exiting method: {}.{}", className, methodName);
        System.out.println("AFTER: Exiting " + className + "." + methodName);
    }

    /**
     * AfterReturning advice: Executes after successful method execution
     */
    @AfterReturning(pointcut = "serviceMethodExecution()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.info("<< Method {}.{} returned: {}", className, methodName, result);
        System.out.println("AFTER-RETURNING: Method " + className + "." + methodName + 
                " returned: " + result);
    }

    /**
     * AfterThrowing advice: Executes if method throws an exception
     */
    @AfterThrowing(pointcut = "serviceMethodExecution()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.error("<< Method {}.{} threw exception: {}", className, methodName, ex.getMessage());
        System.out.println("AFTER-THROWING: Method " + className + "." + methodName + 
                " threw exception: " + ex.getMessage());
    }

    /**
     * Around advice: Wraps the method execution and measures execution time
     */
    @Around("@annotation(com.example.aspect.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        
        System.out.println("AROUND: Starting timing for " + className + "." + methodName);
        
        long startTime = System.currentTimeMillis();
        
        Object result;
        try {
            // Proceed with the method execution
            result = joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            logger.info("Method {}.{} executed in {} ms", className, methodName, executionTime);
            System.out.println("AROUND: Method " + className + "." + methodName + 
                    " executed in " + executionTime + " ms");
        }
        
        return result;
    }
}

