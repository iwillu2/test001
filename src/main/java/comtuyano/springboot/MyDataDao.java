package comtuyano.springboot;

import java.io.Serializable;
import java.util.List;


public interface MyDataDao <T> extends Serializable {
	public List<T> getAll();

}
