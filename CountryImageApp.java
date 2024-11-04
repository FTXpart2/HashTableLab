import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountryImageApp {
    private MyHashTable hashTable;
    private JTextArea countryDisplay;
    private JTextField countryAbbrField;
    private JTextField countryNameField;
    private JTextField imageLandmarkField;
    private JTextField imageUrlField;
    private JTextArea imageDisplay;
    private JButton addCountryButton;
    private JButton deleteCountryButton;
    private JButton addImageButton;
    private JButton deleteImageButton;
    private JButton nextImageButton;
    private JButton previousImageButton;
    private String currentAbbreviation;
    private MyImage[] currentImages;
    private int currentImageIndex = 0;

    public CountryImageApp() {
        hashTable = new MyHashTable();
        initializeUI();
        initializeSampleData();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Country Image App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Country display area
        countryDisplay = new JTextArea(5, 20);
        countryDisplay.setEditable(false);
        frame.add(new JScrollPane(countryDisplay), BorderLayout.NORTH);

        // Country input panel
        JPanel countryPanel = new JPanel();
        countryNameField = new JTextField(10);
        countryAbbrField = new JTextField(2);
        addCountryButton = new JButton("Add Country");
        deleteCountryButton = new JButton("Delete Country");

        countryPanel.add(new JLabel("Country Name:"));
        countryPanel.add(countryNameField);
        countryPanel.add(new JLabel("Abbreviation:"));
        countryPanel.add(countryAbbrField);
        countryPanel.add(addCountryButton);
        countryPanel.add(deleteCountryButton);
        frame.add(countryPanel, BorderLayout.CENTER);

        // Image display area
        imageDisplay = new JTextArea(5, 20);
        imageDisplay.setEditable(false);
        frame.add(new JScrollPane(imageDisplay), BorderLayout.SOUTH);

        // Image input panel
        JPanel imagePanel = new JPanel();
        imageLandmarkField = new JTextField(10);
        imageUrlField = new JTextField(15);
        addImageButton = new JButton("Add Image");
        deleteImageButton = new JButton("Delete Image");
        nextImageButton = new JButton("Next Image");
        previousImageButton = new JButton("Previous Image");

        imagePanel.add(new JLabel("Landmark Name:"));
        imagePanel.add(imageLandmarkField);
        imagePanel.add(new JLabel("Image URL:"));
        imagePanel.add(imageUrlField);
        imagePanel.add(addImageButton);
        imagePanel.add(deleteImageButton);
        imagePanel.add(previousImageButton);
        imagePanel.add(nextImageButton);
        frame.add(imagePanel, BorderLayout.EAST);

        // Button listeners
        addCountryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCountry();
            }
        });

        deleteCountryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCountry();
            }
        });

        addImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addImage();
            }
        });

        deleteImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteImage();
            }
        });

        nextImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextImage();
            }
        });

        previousImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousImage();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void initializeSampleData() {
        // Adding sample data for countries and images
        hashTable.addCountry(new Country("Argentina", "ar"));
        hashTable.addImage(hashTable.getCountry("ar"), new MyImage("Iguazu Falls", "http://example.com/iguazu"));
        hashTable.addImage(hashTable.getCountry("ar"), new MyImage("Buenos Aires", "http://example.com/buenosaires"));

        hashTable.addCountry(new Country("Brazil", "br"));
        hashTable.addImage(hashTable.getCountry("br"), new MyImage("Christ the Redeemer", "http://example.com/christ"));
        hashTable.addImage(hashTable.getCountry("br"), new MyImage("Amazon Rainforest", "http://example.com/amazon"));

        hashTable.addCountry(new Country("Canada", "ca"));
        hashTable.addImage(hashTable.getCountry("ca"), new MyImage("CN Tower", "http://example.com/cntower"));
        hashTable.addImage(hashTable.getCountry("ca"), new MyImage("Banff National Park", "http://example.com/banff"));

        // Update country display
        updateCountryDisplay();
    }

    private void addCountry() {
        String name = countryNameField.getText();
        String abbreviation = countryAbbrField.getText().toLowerCase();
        if (!name.isEmpty() && abbreviation.length() == 2) {
            hashTable.addCountry(new Country(name, abbreviation));
            updateCountryDisplay();
            countryNameField.setText("");
            countryAbbrField.setText("");
        }
    }

    private void deleteCountry() {
        String abbreviation = countryAbbrField.getText().toLowerCase();
        if (!abbreviation.isEmpty()) {
            hashTable.removeCountry(abbreviation);
            updateCountryDisplay();
            countryAbbrField.setText("");
        }
    }

    private void addImage() {
        if (currentAbbreviation != null) {
            String landmark = imageLandmarkField.getText();
            String url = imageUrlField.getText();
            if (!landmark.isEmpty() && !url.isEmpty()) {
                hashTable.addImage(hashTable.getCountry(currentAbbreviation), new MyImage(landmark, url));
                imageLandmarkField.setText("");
                imageUrlField.setText("");
                showCurrentImage();
            }
        }
    }

    private void deleteImage() {
        if (currentAbbreviation != null && currentImages != null && currentImages.length > 0) {
            MyImage currentImage = currentImages[currentImageIndex];
            hashTable.removeImage(hashTable.getCountry(currentAbbreviation), currentImage);
            showCurrentImage();
        }
    }

    private void showCurrentImage() {
        if (currentImages != null && currentImages.length > 0) {
            MyImage currentImage = currentImages[currentImageIndex];
            imageDisplay.setText(currentImage.getLandmark() + ": " + currentImage.getUrl());
        }
    }

    private void showNextImage() {
        if (currentImages != null && currentImages.length > 0) {
            currentImageIndex = (currentImageIndex + 1) % currentImages.length;
            showCurrentImage();
        }
    }

    private void showPreviousImage() {
        if (currentImages != null && currentImages.length > 0) {
            currentImageIndex = (currentImageIndex - 1 + currentImages.length) % currentImages.length;
            showCurrentImage();
        }
    }

    private void updateCountryDisplay() {
        StringBuilder displayText = new StringBuilder();
        for (Country country : hashTable.getCountries()) {
            int imageCount = hashTable.getImages(country).length; // assuming getImages returns an array of MyImage
            displayText.append(String.format("%s - %s - %d\n", country.getName(), country.getAbbreviation(), imageCount));
        }
        countryDisplay.setText(displayText.toString());
    }

   
}
