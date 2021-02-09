package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetween;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> STORAGE = Arrays.asList(
            new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 9, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 9, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 9, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 10, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 10, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 10, 20, 0), "Ужин", 510)
    );

    public static List<MealTo> getWithExcess(List<Meal> meals, int caloriesPerDay) {
        return filteredByStreams(meals, meal -> true, caloriesPerDay);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, Predicate<Meal> filter, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealTo> mealsTo = new ArrayList<>();
        meals.forEach(meal -> {
            if (isBetween(meal.getTime(), startTime, endTime)) {
                mealsTo.add(createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsTo;
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
