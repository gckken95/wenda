package com.nowcoder.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
    private EventType type;
    private int actorId;
    private int entityType;
    private int EntityId;
    private int entityOwnerId;

    private Map<String,String> exts=new HashMap<>();

    public EventModel() {
    }

    public EventModel(EventType type, int actorId, int entityType, int entityId, int entityOwnerId, Map<String, String> exts) {
        this.type = type;
        this.actorId = actorId;
        this.entityType = entityType;
        EntityId = entityId;
        this.entityOwnerId = entityOwnerId;
        this.exts = exts;
    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return EntityId;
    }

    public EventModel setEntityId(int EntityId) {
        this.EntityId = EntityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

    public EventModel setExts(String key,String value){
        exts.put(key,value);
        return this;
    }

    public String getExts(String key) {
        return exts.get(key);
    }
}
