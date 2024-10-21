package com.chanochoca.app.service;

import com.chanochoca.app.entity.MatchResult;
import com.chanochoca.app.entity.models.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactDuplicateService {

    /**
     * Encuentra posibles coincidencias entre una lista de contactos.
     * Compara contactos entre sí para identificar posibles duplicados en base a nombres, apellidos, correos electrónicos,
     * direcciones y códigos postales.
     *
     * @param contacts Lista de contactos a evaluar.
     * @return Lista de objetos MatchResult que contienen los resultados de coincidencias potenciales.
     */
    public List<MatchResult> findPotentialMatches(List<Contact> contacts) {
        List<MatchResult> results = new ArrayList<>();

        // Itera sobre cada par de contactos para compararlos
        for (int i = 0; i < contacts.size(); i++) {
            for (int j = i + 1; j < contacts.size(); j++) {
                Contact contact1 = contacts.get(i);
                Contact contact2 = contacts.get(j);

                // Calcula la precisión de la coincidencia entre los dos contactos
                String accuracy = calculateMatchAccuracy(contact1, contact2);

                // Si la precisión no es "None", agrega el resultado a la lista de resultados
                if (!accuracy.equals("None")) {
                    results.add(new MatchResult(contact1.getId(), contact2.getId(), accuracy));
                }
            }
        }
        return results;
    }

    /**
     * Calcula la precisión de la coincidencia entre dos contactos en base a varios criterios (nombre, apellido, correo electrónico,
     * dirección y código postal). Asigna puntos por cada coincidencia y determina un nivel de precisión en base al puntaje.
     *
     * @param contact1 Primer contacto.
     * @param contact2 Segundo contacto.
     * @return Nivel de precisión de la coincidencia ("High", "Medium", "Low", "None").
     */
    private String calculateMatchAccuracy(Contact contact1, Contact contact2) {
        int score = 0;

        // Comparación de nombres
        if (contact1.getFirstName() != null && contact2.getFirstName() != null) {
            if (areSimilarFirstNameOrLastName(contact1.getFirstName(), contact2.getFirstName())) {
                score += 30;
            } else if (contact1.getFirstName().substring(0, 1).equalsIgnoreCase(contact2.getFirstName().substring(0, 1))) {
                score += 15;
            }
        }

        // Comparación de apellidos
        if (contact1.getLastName() != null && contact2.getLastName() != null) {
            if (areSimilarFirstNameOrLastName(contact1.getLastName(), contact2.getLastName())) {
                score += 30;
            } else if (contact1.getLastName().substring(0, 1).equalsIgnoreCase(contact2.getLastName().substring(0, 1))) {
                score += 15;
            }
        }

        // Comparación de correos electrónicos
        if (contact1.getEmail() != null && contact2.getEmail() != null) {
            if (areSimilarEmail(contact1.getEmail(), contact2.getEmail())) {
                score += 30;
            } else {
                String cleanedEmail1 = cleanEmail(contact1.getEmail());
                String cleanedEmail2 = cleanEmail(contact2.getEmail());
                if (cleanedEmail1.equals(cleanedEmail2)) {
                    score += 20;
                }
            }
        }

        // Comparación de códigos postales
        if (areSimilarZipCode(contact1.getPostalZip(), contact2.getPostalZip())) {
            score += 20;
        }

        // Comparación de direcciones
        if (areSimilarAddress(contact1.getAddress(), contact2.getAddress())) {
            score += 20;
        }

        // Determina el nivel de precisión basado en el puntaje total
        return determineAccuracyLevel(score);
    }

    /**
     * Verifica si dos cadenas de texto (nombres o apellidos) son similares.
     * La comparación ignora espacios, caracteres especiales y hace la comparación sin distinguir entre mayúsculas y minúsculas.
     *
     * @param str1 Primer nombre o apellido.
     * @param str2 Segundo nombre o apellido.
     * @return true si son similares, false de lo contrario.
     */
    private boolean areSimilarFirstNameOrLastName(String str1, String str2) {
        if (str1 != null && str2 != null) {
            String cleanedStr1 = str1.trim().replaceAll("\\s+", "").replaceAll("[^a-zA-Z]", "").toLowerCase();
            String cleanedStr2 = str2.trim().replaceAll("\\s+", "").replaceAll("[^a-zA-Z]", "").toLowerCase();
            return cleanedStr1.equals(cleanedStr2);
        }
        return false;
    }

    /**
     * Verifica si dos correos electrónicos son similares.
     * La comparación elimina espacios y caracteres especiales no válidos.
     *
     * @param str1 Primer correo electrónico.
     * @param str2 Segundo correo electrónico.
     * @return true si son similares, false de lo contrario.
     */
    private boolean areSimilarEmail(String str1, String str2) {
        if (str1 != null && str2 != null) {
            String cleanedStr1 = str1.trim().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9@.]", "").toLowerCase();
            String cleanedStr2 = str2.trim().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9@.]", "").toLowerCase();
            return cleanedStr1.equals(cleanedStr2);
        }
        return false;
    }

    /**
     * Limpia un correo electrónico eliminando los caracteres especiales antes del dominio.
     *
     * @param email Correo electrónico a limpiar.
     * @return Correo electrónico limpio.
     */
    private String cleanEmail(String email) {
        return email.split("@")[0].replaceAll("[@.]", "").toLowerCase();
    }

    /**
     * Verifica si dos códigos postales son similares.
     *
     * @param zipCode1 Primer código postal.
     * @param zipCode2 Segundo código postal.
     * @return true si son similares, false de lo contrario.
     */
    private boolean areSimilarZipCode(String zipCode1, String zipCode2) {
        if (zipCode1 != null && zipCode2 != null) {
            String cleanedZipCode1 = zipCode1.trim();
            String cleanedZipCode2 = zipCode2.trim();
            return cleanedZipCode1.equals(cleanedZipCode2);
        }
        return false;
    }

    /**
     * Verifica si dos direcciones son similares.
     * Ignora espacios y algunos caracteres especiales.
     *
     * @param address1 Primera dirección.
     * @param address2 Segunda dirección.
     * @return true si son similares, false de lo contrario.
     */
    private boolean areSimilarAddress(String address1, String address2) {
        if (address1 != null && address2 != null) {
            String cleanedAddress1 = address1.trim().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9.,#-]", "").toLowerCase();
            String cleanedAddress2 = address2.trim().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9.,#-]", "").toLowerCase();
            return cleanedAddress1.equals(cleanedAddress2);
        }
        return false;
    }

    /**
     * Determina el nivel de coincidencia en base al puntaje acumulado.
     *
     * @param score Puntaje calculado de la coincidencia.
     * @return Nivel de precisión: "High", "Medium", "Low" o "None".
     */
    private String determineAccuracyLevel(int score) {
        if (score >= 90) {
            return "High";
        } else if (score >= 60) {
            return "Medium";
        } else if (score >= 40) {
            return "Low";
        } else {
            return "None";
        }
    }
}
