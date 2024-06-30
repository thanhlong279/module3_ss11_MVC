package com.example.mvcmanagementproduct.repositories;

import com.example.mvcmanagementproduct.models.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static String FILE_PRODUCT = "D:\\code_gym\\module_3_SQL\\session\\web_servlet\\ss11_MVC\\mvc-management-product\\src\\main\\java\\com\\example\\mvcmanagementproduct\\repositories\\products.csv";

    private static ProductRepository instance;

    private ProductRepository() {
    }

    public synchronized static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

public void add(Product product) {
    List<Product> products = readFile(FILE_PRODUCT);
    products.add(product);
    writeFile(products, false, FILE_PRODUCT);
}

    private List<Product> readFile(String fileName) {
        List<Product> products = new ArrayList<>();
        File file = new File(fileName);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                products.add(new Product(Integer.parseInt(temp[0]), temp[1], Double.parseDouble(temp[2]), temp[3], temp[4]));
            }
        } catch (FileNotFoundException e) {
            System.out.println(" ko tìm thấy file");
        } catch (IOException e) {
            System.out.println(" lỗi đọc dữ liệu");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("lỗi đóng file");
                }
            }
        }
        return products;
    }

    private void writeFile(List<Product> products, boolean apprnd, String fileName) {
        File file = new File(fileName);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, apprnd);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (Product product : products) {
                bufferedWriter.write(toString(product));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("lỗi ghi file");
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    System.out.println("lỗi đóng file");
                }
            }
        }
    }

    private String toString(Product product) {
        return product.getId()+","+product.getName()+","+ product.getPrice()+","+product.getDescription()+","+product.getProducer();
    }

    public List<Product> getAll() {
        return new ArrayList<>(readFile(FILE_PRODUCT));
    }

    public void remove(int id) {
        List<Product> products = readFile(FILE_PRODUCT);
        for (Product product : products) {
            if (product.getId() == id) {
                products.remove(product);
            }
        }
    }

    public void update(int id, Product product) {
        List<Product> products = readFile(FILE_PRODUCT);
        products.set(id, product);
        writeFile(products, false, FILE_PRODUCT);
    }
}
