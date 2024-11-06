import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountryUI {
    private JFrame frame;
    private MyHashTable countryTable;
    private Country currentCountry;
    private int currentImageIndex;
    private JTextArea countryListTextArea;

    public CountryUI() {
        countryTable = new MyHashTable();
        initializeCountries();
        setupUI();
        updateCountryListDisplay();
    }

    private void initializeCountries() {
        Country argentina = new Country("Argentina", "ar");
        countryTable.addCountry(argentina);
        countryTable.addImage(argentina, new MyImage("Iguazu Falls", "https://upload.wikimedia.org/wikipedia/commons/a/a4/Iguazu_Falls_Argentina.jpg"));
        countryTable.addImage(argentina, new MyImage("Buenos Aires", "https://upload.wikimedia.org/wikipedia/commons/e/e1/Buenos_Aires_Montserrat.jpg"));

        Country brazil = new Country("Brazil", "br");
        countryTable.addCountry(brazil);
        countryTable.addImage(brazil, new MyImage("Christ the Redeemer", "https://upload.wikimedia.org/wikipedia/commons/8/8e/Christ_the_Redeemer_Statue.jpg"));
        countryTable.addImage(brazil, new MyImage("Amazon Rainforest", "https://upload.wikimedia.org/wikipedia/commons/5/56/Amazon_Rainforest_Brazil.jpg"));

        Country canada = new Country("Canada", "ca");
        countryTable.addCountry(canada);
        countryTable.addImage(canada, new MyImage("Niagara Falls", "https://upload.wikimedia.org/wikipedia/commons/e/e4/Niagara_Falls_Canada.jpg"));
        countryTable.addImage(canada, new MyImage("Banff National Park", "https://upload.wikimedia.org/wikipedia/commons/1/1e/Banff_National_Park.jpg"));
    }

    private void setupUI() {
        frame = new JFrame("Country Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Country selection panel
        JPanel countrySelectionPanel = new JPanel();
        JTextField countryInput = new JTextField(5);
        JButton selectCountryButton = new JButton("Select Country");

        selectCountryButton.addActionListener(e -> {
            String abbreviation = countryInput.getText().toLowerCase();
            currentCountry = getCountryByAbbreviation(abbreviation);
            if (currentCountry != null) {
                currentImageIndex = 0;
                viewCountryImages();
            } else {
                JOptionPane.showMessageDialog(frame, "Country not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        countrySelectionPanel.add(new JLabel("Enter Country Abbreviation: "));
        countrySelectionPanel.add(countryInput);
        countrySelectionPanel.add(selectCountryButton);

        frame.add(countrySelectionPanel, BorderLayout.NORTH);

        // Country list display area
        countryListTextArea = new JTextArea(10, 30);
        countryListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(countryListTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Image navigation and action panel
        JPanel imageNavigationPanel = new JPanel();
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton deleteButton = new JButton("Delete Current Image");
        JButton backButton = new JButton("Back");

        prevButton.addActionListener(e -> {
            if (currentCountry != null && currentImageIndex > 0) {
                currentImageIndex--;
                viewCountryImages();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentCountry != null && currentImageIndex < getImagesOfCountry(currentCountry).size() - 1) {
                currentImageIndex++;
                viewCountryImages();
            }
        });

        deleteButton.addActionListener(e -> {
            if (currentCountry != null) {
                MyImage imageToRemove = getImagesOfCountry(currentCountry).get(currentImageIndex);
                countryTable.removeImage(currentCountry, imageToRemove);
                if (getImagesOfCountry(currentCountry).size() == 0) {
                    JOptionPane.showMessageDialog(frame, "No more images left for this country!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    currentCountry = null;
                } else {
                    if (currentImageIndex >= getImagesOfCountry(currentCountry).size()) {
                        currentImageIndex = getImagesOfCountry(currentCountry).size() - 1;
                    }
                }
                viewCountryImages();
                updateCountryListDisplay();
            }
        });

        backButton.addActionListener(e -> {
            currentCountry = null;
            JOptionPane.showMessageDialog(frame, "Back to country selection.");
        });

        imageNavigationPanel.add(prevButton);
        imageNavigationPanel.add(nextButton);
        imageNavigationPanel.add(deleteButton);
        imageNavigationPanel.add(backButton);
        frame.add(imageNavigationPanel, BorderLayout.SOUTH);

        // Add Country and Add Image panel
        JPanel addPanel = new JPanel(new GridLayout(3, 1));

        // Add country section
        JPanel addCountryPanel = new JPanel();
        JTextField countryNameField = new JTextField(10);
        JTextField countryAbbreviationField = new JTextField(5);
        JButton addCountryButton = new JButton("Add Country");

        addCountryButton.addActionListener(e -> {
            String name = countryNameField.getText().trim();
            String abbreviation = countryAbbreviationField.getText().trim().toLowerCase();
            if (!name.isEmpty() && !abbreviation.isEmpty() && abbreviation.length() == 2) {
                if (getCountryByAbbreviation(abbreviation) == null) {
                    Country newCountry = new Country(name, abbreviation);
                    countryTable.addCountry(newCountry);
                    updateCountryListDisplay();
                    JOptionPane.showMessageDialog(frame, "Country added successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Country abbreviation already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter valid name and abbreviation!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addCountryPanel.add(new JLabel("Country Name: "));
        addCountryPanel.add(countryNameField);
        addCountryPanel.add(new JLabel("Abbreviation: "));
        addCountryPanel.add(countryAbbreviationField);
        addCountryPanel.add(addCountryButton);
        addPanel.add(addCountryPanel);

        // Delete country section
        JPanel deleteCountryPanel = new JPanel();
        JTextField deleteCountryField = new JTextField(5);
        JButton deleteCountryButton = new JButton("Delete Country");

        deleteCountryButton.addActionListener(e -> {
            String abbreviation = deleteCountryField.getText().trim().toLowerCase();
            Country countryToRemove = getCountryByAbbreviation(abbreviation);
            if (countryToRemove != null) {
                countryTable.removeCountry(countryToRemove);
                updateCountryListDisplay();
                JOptionPane.showMessageDialog(frame, "Country deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Country not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteCountryPanel.add(new JLabel("Country Abbreviation: "));
        deleteCountryPanel.add(deleteCountryField);
        deleteCountryPanel.add(deleteCountryButton);
        addPanel.add(deleteCountryPanel);

        // Add image section
        JPanel addImagePanel = new JPanel();
        JTextField imageUrlField = new JTextField(15);
        JTextField landmarkNameField = new JTextField(10);
        JButton addImageButton = new JButton("Add Image");

        addImageButton.addActionListener(e -> {
            if (currentCountry != null) {
                String url = imageUrlField.getText().trim();
                String landmark = landmarkNameField.getText().trim();
                if (!url.isEmpty() && !landmark.isEmpty()) {
                    MyImage newImage = new MyImage(landmark, url);
                    countryTable.addImage(currentCountry, newImage);
                    updateCountryListDisplay();
                    JOptionPane.showMessageDialog(frame, "Image added successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid URL and landmark name!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a country first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addImagePanel.add(new JLabel("Image URL: "));
        addImagePanel.add(imageUrlField);
        addImagePanel.add(new JLabel("Landmark Name: "));
        addImagePanel.add(landmarkNameField);
        addImagePanel.add(addImageButton);
        addPanel.add(addImagePanel);

        frame.add(addPanel, BorderLayout.EAST);

        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    private void viewCountryImages() {
        if (currentCountry != null) {
            MyImage currentImage = getImagesOfCountry(currentCountry).get(currentImageIndex);
            if (currentImage != null) {
                String message = String.format("Country: %s - %s\nLandmark: %s\nImage URL: %s",
                        currentCountry.getName(),
                        currentCountry.getAbbreviation(),
                        currentImage.getLandmarkName(),
                        currentImage.getUrl());
                JOptionPane.showMessageDialog(frame, message, "Image Viewer", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void updateCountryListDisplay() {
        StringBuilder countryList = new StringBuilder("Available Countries:\n");
        for (Country country : countryTable.getCountries()) {
            countryList.append(String.format("%s - %s - %d images\n",
                    country.getName(),
                    country.getAbbreviation(),
                    countryTable.getImages(country).size()));
        }
        countryListTextArea.setText(countryList.toString());
    }

    private Country getCountryByAbbreviation(String abbreviation) {
        for (Country country : countryTable.getCountries()) {
            if (country.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                return country;
            }
        }
        return null;
    }

    private DLList<MyImage> getImagesOfCountry(Country country) {
        return countryTable.getImages(country);
    }

  
}
