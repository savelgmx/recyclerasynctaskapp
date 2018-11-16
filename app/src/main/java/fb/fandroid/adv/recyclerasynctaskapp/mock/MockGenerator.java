package fb.fandroid.adv.recyclerasynctaskapp.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 09.10.2018.
 * * генератор заглушечных данных,  то есть  имитация запроса в сеть либо к базе данных
 */
public class MockGenerator {
/*
Создадим статический метод generate. Он должен возвращать List<Mock>.
На вход будет принимать количество элементов, которые нужно сгенерировать – int count.
Прописываем mocks сразу нужного размера).
Нужно передать название и value, давайте их тоже будем генерировать.
 */
    public static List<Mock> generate(int count){
        List<Mock> mocks = new ArrayList<>(count);
        Random random = new Random();

        for(int i = 0; i<count;i++){
            mocks.add(new Mock(UUID.randomUUID().toString(), random.nextInt(200) ));
        }

        return mocks;
    }

}
