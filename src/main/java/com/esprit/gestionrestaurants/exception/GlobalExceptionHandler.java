package com.esprit.gestionrestaurants.exception;

import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Ressource absente (ex. restaurant inconnu) → HTTP 404. */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Paramètre invalide : " + ex.getName() + "."));
    }

    /** JSON mal formé ou types incompatibles (ex. enum inconnu). */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleNotReadable(HttpMessageNotReadableException ex) {
        String message =
                "Corps JSON invalide ou champ mal typé (vérifie les enums et les nombres). "
                        + "Si ça persiste : catégorie exacte FAST_FOOD, CAFE, RESTAURANT ou PATISSERIE "
                        + "(voir CategorieRestaurant.java) ; fraisLivraison en nombre JSON (ex. 5 ou 5.5), "
                        + "pas de chaîne vide pour un champ numérique obligatoire. "
                        + "Angular : lier fraisLivraison en number ; option environment.restaurantApi.fraisLivraisonAsInteger "
                        + "(true = arrondi entier côté front si tu dois éviter les décimales ; false = garder décimal / BigDecimal côté front).";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", message));
    }

    /** Contrainte SQL (NOT NULL, FK, UNIQUE) — souvent champs manquants ou doublon. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "message",
                        "Données refusées par la base (champ obligatoire manquant, doublon ou référence invalide)."));
    }
}
