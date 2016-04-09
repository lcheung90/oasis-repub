package csci567.project.oasis;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;

/**
 * Created by Cheung on 4/9/2016.
 */
public class CloudantSingleton {
    private static CloudantSingleton mInstance = null;

    private CloudantClient client;

    private CloudantSingleton(){
        client =  ClientBuilder.account("lamamafalsa")
                .username("lamamafalsa")
                .password("1234567890")
                .build();
    }

    public static CloudantSingleton getInstance(){
        if(mInstance == null){
            mInstance = new CloudantSingleton();
        }
        return mInstance;
    }

    public CloudantClient getClient(){
        return this.client;
    }
}
