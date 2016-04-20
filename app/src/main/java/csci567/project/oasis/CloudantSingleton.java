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
        client =  ClientBuilder.account("1ceb4ff2-441b-4bd7-bab3-cafbae69621b-bluemix")
                .username("1ceb4ff2-441b-4bd7-bab3-cafbae69621b-bluemix")
                .password("3efd61b3011f6dc80ef998dac5765b80b065656f6228f4cc1158e099a6ac4f27")
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
