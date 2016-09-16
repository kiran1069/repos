package testsample.altvr.com.testsample.service;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import testsample.altvr.com.testsample.PixabayRetrofitService;
import testsample.altvr.com.testsample.RetrofitAdapter;
import testsample.altvr.com.testsample.events.PhotosEvent;
import testsample.altvr.com.testsample.util.LogUtil;
import testsample.altvr.com.testsample.vo.PhotoResponseVo;

public class ApiService implements  Callback<PhotoResponseVo> {
    private LogUtil log = new LogUtil(ApiService.class);
    private static String PIXABAY_API_KEY = "2387134-2e9952af7d840c1d7abc947b1";
    private static int MIN_IMAGE_WIDTH = 1000;
    private static int MIN_IMAGE_HEIGHT = 1000;
    private static String IMAGE_TYPE = "photo";

    private PixabayRetrofitService mService;
    private EventBus mEventBus;

    public ApiService(Context context) {
        mService = RetrofitAdapter.getRestService(context);
        mEventBus = EventBus.getDefault();
    }


     public void getDefaultPhotos() {

            mService.getDefaultPhotos(PIXABAY_API_KEY, MIN_IMAGE_WIDTH, MIN_IMAGE_HEIGHT, IMAGE_TYPE, this);
     }


    public void searchPhotos(String query) {
            mService.searchPhotos(PIXABAY_API_KEY, query, MIN_IMAGE_WIDTH, MIN_IMAGE_HEIGHT, IMAGE_TYPE, this);
    }

    @Override
    public void success(PhotoResponseVo photoResponseVo, Response response) {
        LogUtil.log("SUCCESS GETTING VALUES ");
        if (photoResponseVo != null) {
            PhotosEvent events = new PhotosEvent(photoResponseVo.hits);
            mEventBus.post(events);
        }

    }

    @Override
    public void failure(RetrofitError error) {
        LogUtil.log("SUCCESS GETTING VALUES "+error.getMessage());
    }
    /**
     * YOUR CODE HERE
     *
     * For part 1a, you should implement getDefaultPhotos and searchPhotos. These calls should make the proper
     * API calls to Pixabay and post PhotosEvents to the event bus for the fragments to fill themselves in.
     *
     * We provide a Retrofit API adapter here you can use, or you can roll your own using the HTTP library
     * of your choice.
     */
}
