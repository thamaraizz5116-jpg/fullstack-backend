package com.lastmile.backend.config;

import com.lastmile.backend.entity.*;
import com.lastmile.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final SystemUserRepository userRepository;
    private final MerchantStoreRepository storeRepository;
    private final DeliveryTaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(SystemUserRepository userRepository,
                          MerchantStoreRepository storeRepository,
                          DeliveryTaskRepository taskRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            System.out.println("Database already has data. Skipping seeding.");
            return;
        }

        System.out.println("Seeding database with default users, Amazon & Flipkart stores, and sample tasks...");

        // 1. Create Users
        SystemUser admin = new SystemUser(null, "admin", "admin@lastmile.com", passwordEncoder.encode("admin123"), Role.ADMIN);
        SystemUser merchant = new SystemUser(null, "merchant", "merchant@lastmile.com", passwordEncoder.encode("merchant123"), Role.MERCHANT);
        SystemUser courier = new SystemUser(null, "courier", "courier@lastmile.com", passwordEncoder.encode("courier123"), Role.COURIER);

        userRepository.saveAll(List.of(admin, merchant, courier));

        // 2. Create Stores (Amazon & Flipkart)
        MerchantStore amazonStore = new MerchantStore(null, "Amazon Fulfillment Center", "Sector 62, Noida, UP - 201301", "+91 1800 3000 9009", merchant);
        MerchantStore flipkartStore = new MerchantStore(null, "Flipkart Hub", "Electronic City, Bangalore, KA - 560100", "+91 1800 208 9898", merchant);
        MerchantStore localMart = new MerchantStore(null, "Fresh Mart Local", "T. Nagar, Chennai, TN - 600017", "+91 98765 43210", merchant);

        storeRepository.saveAll(List.of(amazonStore, flipkartStore, localMart));

        // 3. Create Sample Delivery Tasks
        DeliveryTask task1 = new DeliveryTask(null, "Amazon Fulfillment Center, Noida", "A-45, Sector 58, Noida", "Rahul Sharma", "+91 99887 76655", DeliveryStatus.PENDING, amazonStore, null);
        DeliveryTask task2 = new DeliveryTask(null, "Flipkart Hub, Bangalore", "12, 4th Cross, HSR Layout, Bangalore", "Priya Nair", "+91 88776 65544", DeliveryStatus.IN_TRANSIT, flipkartStore, courier);
        DeliveryTask task3 = new DeliveryTask(null, "Fresh Mart Local, Chennai", "56, Usman Road, T. Nagar, Chennai", "Vijay Kumar", "+91 77665 54433", DeliveryStatus.DELIVERED, localMart, courier);
        DeliveryTask task4 = new DeliveryTask(null, "Amazon Fulfillment Center, Noida", "B-12, Sector 62, Noida", "Amit Patel", "+91 66554 43322", DeliveryStatus.PICKED_UP, amazonStore, courier);

        taskRepository.saveAll(List.of(task1, task2, task3, task4));

        System.out.println("Seeding completed successfully!");
    }
}
