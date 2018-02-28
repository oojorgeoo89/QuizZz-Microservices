package jorge.rv.quizzz.quizservice.service.accesscontrol.aspects;

import org.aspectj.lang.ProceedingJoinPoint;

import jorge.rv.quizzz.quizservice.model.BaseModel;
import jorge.rv.quizzz.quizservice.model.UserOwned;
import jorge.rv.quizzz.quizservice.service.accesscontrol.AccessControlService;

public abstract class AccessControlAspectsAbs<T extends BaseModel & UserOwned> implements AccessControlAspects<T> {

	@Override
	public Object save(ProceedingJoinPoint proceedingJoinPoint, T object) throws Throwable {
		if (object.getId() == null) {
			getAccessControlService().canCurrentUserCreateObject(object);
		} else {
			getAccessControlService().canCurrentUserUpdateObject(object);
		}

		return proceedingJoinPoint.proceed();
	}

	@Override
	public Object find(ProceedingJoinPoint proceedingJoinPoint, Long id) throws Throwable {
		getAccessControlService().canCurrentUserReadObject(id);

		return proceedingJoinPoint.proceed();
	}

	@Override
	public Object findAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		getAccessControlService().canCurrentUserReadAllObjects();

		return proceedingJoinPoint.proceed();
	}

	@Override
	public Object delete(ProceedingJoinPoint proceedingJoinPoint, T object) throws Throwable {
		getAccessControlService().canCurrentUserDeleteObject(object);

		return proceedingJoinPoint.proceed();
	}

	protected abstract AccessControlService<T> getAccessControlService();

}
