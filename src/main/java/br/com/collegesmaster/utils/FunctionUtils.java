package br.com.collegesmaster.utils;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import br.com.collegesmaster.model.entities.model.Model;

public abstract class FunctionUtils {

	public static <T extends Model> Boolean existsFieldInEntity(final String field, final Class<T> model) {
		try {
			model.getDeclaredField(field);
			return TRUE;
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return FALSE;
		}
	}
}
