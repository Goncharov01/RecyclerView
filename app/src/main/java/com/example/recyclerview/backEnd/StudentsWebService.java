package com.example.recyclerview.backEnd;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import com.example.recyclerview.backEnd.entities.Student;
import com.example.recyclerview.util.ICallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class StudentsWebService implements IWebService<Student> {

    private static final String DEFAULT_NAME = "Student";
    private static final int MAX_COLOR_VALUE = 255;
    private static final int MAX_HW_COUNT = 10;
    private static final int START_ENTITIES_COUNT = 10;

    private Random random = new Random();
    private List<Student> students = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());

    private long idCount;

    {
        for (int i = 0; i < START_ENTITIES_COUNT; i++) {
            Student student = new Student();

            student.setId(idCount++);
            student.setHwCount(1 + random.nextInt(MAX_HW_COUNT));
            student.setName(DEFAULT_NAME + i);
            student.setColorArgb(Color.argb(
                    MAX_COLOR_VALUE,
                    random.nextInt(MAX_COLOR_VALUE),
                    random.nextInt(MAX_COLOR_VALUE),
                    random.nextInt(MAX_COLOR_VALUE)));

            students.add(student);
        }
    }

    @Override
    public void getEntities(final ICallback<List<Student>> callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResult(students);
            }
        });
    }

    @Override
    public void getEntities(final int startRange, final int endRange, final ICallback<List<Student>> callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResult(students.subList(startRange, endRange));
            }
        });
    }

    @Override
    public void removeEntity(final Long id) {
        Iterator<Student> iterator = students.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getId() ==  id) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void addEntity(final String name, final int hwCount) {
        Student student = new Student();

        student.setId(idCount++);
        student.setHwCount(hwCount);
        student.setName(name);
        student.setColorArgb(Color.argb(
                MAX_COLOR_VALUE,
                random.nextInt(MAX_COLOR_VALUE),
                random.nextInt(MAX_COLOR_VALUE),
                random.nextInt(MAX_COLOR_VALUE)));

        students.add(student);
    }

    @Override
    public void editEntity(final Long id, final String name, final int hwCount) {
        Iterator<Student> iterator = students.iterator();

        while (iterator.hasNext()) {
            Student student = iterator.next();

            if (student.getId() == id) {
                student.setName(name);
                student.setHwCount(hwCount);
                break;
            }
        }
    }
}