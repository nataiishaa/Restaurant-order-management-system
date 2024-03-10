package com.developer.Restaurant_manager_appIDZ.services;

import com.developer.Restaurant_manager_appIDZ.model.Dish;
import com.developer.Restaurant_manager_appIDZ.model.Order;
import com.developer.Restaurant_manager_appIDZ.model.Status;
import com.developer.Restaurant_manager_appIDZ.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class CookingService {
    final int NUMBER_OF_CHEFS = 2;
    private final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CHEFS);
    private final LinkedBlockingQueue<Order> orders;
    private Function<Order, Void> makeOrderReady;

    public CookingService() {
        orders = new LinkedBlockingQueue<Order>();
        startCooking();
    }

    public void addOrderToQueue(Order order, Function<Order, Void> callback) {
        orders.add(order);
        this.makeOrderReady = callback;
    }

    public void startCooking() {
        Runnable task = () -> {
            while (true) {
                try {
                    Order order = orders.take();
                    if (!order.getDishes().isEmpty()) {
                        executorService.execute(() -> processOrder(order));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };

        executorService.execute(task);
    }

    private void processOrder(Order order) {
        for (Dish dish : order.getDishes()) {
            if (order.getStatus() == Status.CANCELED) {
                System.out.println("Order has been canceled");
            }
            cookDish(dish);
        }
        order.setStatus(Status.READY);
        makeOrderReady.apply(order);
        System.out.println("The order is ready");
    }

    private void cookDish(Dish dish) {
        executorService.submit(() -> {
            try {
                Thread.sleep(dish.getCookTime() * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.MILLISECONDS)) {
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}