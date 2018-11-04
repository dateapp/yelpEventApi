
package com.amazonaws.lambda.yelpeventapi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "alias",
    "name",
    "image_url",
    "is_closed",
    "url",
    "review_count",
    "categories",
    "rating",
    "coordinates",
    "transactions",
    "price",
    "location",
    "phone",
    "display_phone",
    "distance"
})
public class Business {

    @JsonProperty("id")
    private String id;
    @JsonProperty("alias")
    private String alias;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("is_closed")
    private Boolean isClosed;
    @JsonProperty("url")
    private String url;
    @JsonProperty("review_count")
    private Integer reviewCount;
    @JsonProperty("categories")
    private List<Category> categories = null;
    @JsonProperty("rating")
    private Double rating;
    @JsonProperty("coordinates")
    private Coordinates coordinates;
    @JsonProperty("transactions")
    private List<String> transactions = null;
    @JsonProperty("price")
    private String price;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("display_phone")
    private String displayPhone;
    @JsonProperty("distance")
    private Double distance;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("alias")
    public String getAlias() {
        return alias;
    }

    @JsonProperty("alias")
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("is_closed")
    public Boolean getIsClosed() {
        return isClosed;
    }

    @JsonProperty("is_closed")
    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("review_count")
    public Integer getReviewCount() {
        return reviewCount;
    }

    @JsonProperty("review_count")
    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    @JsonProperty("categories")
    public List<Category> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @JsonProperty("rating")
    public Double getRating() {
        return rating;
    }

    @JsonProperty("rating")
    public void setRating(Double rating) {
        this.rating = rating;
    }

    @JsonProperty("coordinates")
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @JsonProperty("coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @JsonProperty("transactions")
    public List<String> getTransactions() {
        return transactions;
    }

    @JsonProperty("transactions")
    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("display_phone") 
    public String getDisplayPhone() {
        return displayPhone;
    }

    @JsonProperty("display_phone")
    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    @JsonProperty("distance")
    public Double getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
