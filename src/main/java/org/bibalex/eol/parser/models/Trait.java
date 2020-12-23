package org.bibalex.eol.parser.models;

import java.util.ArrayList;

public class Trait {
    String traitId;
    int resourceId;
    String taxonId;
    String scientificName;
    TraitLocation location;
    String bibliographicCitation;
    String measurementType;
    String measurementUnit;
    String normalizedMeasurementValue;
    String normalizedMeasurementUnit;
    String statisticalMethod;
    String source;
    ArrayList<Reference> references =  new ArrayList<>();
    String targetTaxonId;
    String targetScientificName;
    String measurementValue;
    String measurement;
    String literal;
    String lifestage;
    String sex;

    public String getTraitId() {
        return traitId;
    }

    public void setTraitId(String traitId) {
        this.traitId = traitId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }


    public String getBibliographicCitation() {
        return bibliographicCitation;
    }

    public void setBibliographicCitation(String bibliographicCitation) {
        this.bibliographicCitation = bibliographicCitation;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getNormalizedMeasurementValue() {
        return normalizedMeasurementValue;
    }

    public void setNormalizedMeasurementValue(String normalizedMeasurementValue) {
        this.normalizedMeasurementValue = normalizedMeasurementValue;
    }

    public String getNormalizedMeasurementUnit() {
        return normalizedMeasurementUnit;
    }

    public void setNormalizedMeasurementUnit(String normalizedMeasurementUnit) {
        this.normalizedMeasurementUnit = normalizedMeasurementUnit;
    }

    public String getStatisticalMethod() {
        return statisticalMethod;
    }

    public void setStatisticalMethod(String statisticalMethod) {
        this.statisticalMethod = statisticalMethod;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<Reference> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<Reference> references) {
        this.references = references;
    }


    public String getTargetTaxonId() {
        return targetTaxonId;
    }

    public void setTargetTaxonId(String targetTaxonId) {
        this.targetTaxonId = targetTaxonId;
    }

    public String getTargetScientificName() {
        return targetScientificName;
    }

    public void setTargetScientificName(String targetScientificName) {
        this.targetScientificName = targetScientificName;
    }

    public String getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(String measurementValue) {
        this.measurementValue = measurementValue;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public String getLifestage() {
        return lifestage;
    }

    public void setLifestage(String lifestage) {
        this.lifestage = lifestage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTaxonId() {
        return taxonId;
    }

    public void setTaxonId(String taxonId) {
        this.taxonId = taxonId;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public TraitLocation getLocation() {
        return location;
    }

    public void setLocation(TraitLocation locationInfo) {
        this.location = locationInfo;
    }

    public Trait(String traitId, int resourceId, String taxonId, String scientificName, String bibliographicCitation, String measurementUnit, String normalizedMeasurementValue,
                 String normalizedMeasurementUnit, String statisticalMethod, String source,
                 String measurementValue, String measurement, String literal, String lifestage,
                 String sex, TraitLocation locationInfo)
    {
        this.traitId = traitId;
        this.resourceId = resourceId;
        this.taxonId = taxonId;
        this.scientificName = scientificName;
        this.bibliographicCitation = bibliographicCitation;
        this.measurementUnit = measurementUnit;
        this.normalizedMeasurementValue = normalizedMeasurementValue;
        this.normalizedMeasurementUnit = normalizedMeasurementUnit;
        this.statisticalMethod = statisticalMethod;
        this.source = source;
        this.measurementValue = measurementValue;
        this.measurement = measurement;
        this.literal = literal;
        this.lifestage = lifestage;
        this.sex = sex;
        this.location = locationInfo;
    }


    }

