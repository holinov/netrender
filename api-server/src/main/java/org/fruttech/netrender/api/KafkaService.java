package org.fruttech.netrender.api;

import java.io.Externalizable;

interface KafkaService {
    <T extends Externalizable> void  put(T msg, String topic);
}
