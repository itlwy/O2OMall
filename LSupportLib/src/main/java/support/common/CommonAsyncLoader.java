package support.common;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;

@SuppressLint("NewApi") public abstract class CommonAsyncLoader<T,K> extends AsyncTaskLoader<T> {

	private Context context;
	private T mData;
	private K mObserver;
	
	@SuppressLint("NewApi") public CommonAsyncLoader(Context context) {
		super(context);
		this.context = context;
	}
	  
	  
	  @Override  
	  public void deliverResult(T data) {  
	    if (isReset()) {  
	      // The Loader has been reset; ignore the result and invalidate the data.  
	    if(data != null)  
	    	releaseResources(data);  
	      return;  
	    }  
	  
	    // Hold a reference to the old data so it doesn't get garbage collected.  
	    // We must protect it until the new data has been delivered.  
	    T oldData = mData;  
	    mData = data;  
	  
	    if (isStarted()) {  
	      // If the Loader is in a started state, deliver the results to the  
	      // client. The superclass method does this for us.  
	      super.deliverResult(data);  
	    }  
	  
	    // Invalidate the old data as we don't need it any more.  
	    if (oldData != null && oldData != data) {  
	      releaseResources(oldData);  
	    }  
	  }  
	  
	  /*********************************************************/  
	  /** (3) Implement the Loader’s state-dependent behavior **/  
	  /*********************************************************/  
	  
	  @Override  
	  protected void onStartLoading() {  
	    if (mData != null) {  
	      // Deliver any previously loaded data immediately.  
	      deliverResult(mData);  
	    }  
	   
	    // Begin monitoring the underlying data source.  
	    if (mObserver == null) {
	    	 mObserver = initOberver();
	      }
	    
	    if (takeContentChanged() || mData == null) {  
	      // When the observer detects a change, it should call onContentChanged()  
	      // on the Loader, which will cause the next call to takeContentChanged()  
	      // to return true. If this is ever the case (or if the current data is  
	      // null), we force a new load.  
	      forceLoad();  
	    }  
	  }  

	@Override  
	  protected void onStopLoading() {  
	    // The Loader is in a stopped state, so we should attempt to cancel the   
	    // current load (if there is one).  
	    cancelLoad();  
	  
	    // Note that we leave the observer as is. Loaders in a stopped state  
	    // should still monitor the data source for changes so that the Loader  
	    // will know to force a new load if it is ever started again.  
	  }  
	  
	  @Override  
	  protected void onReset() {  
	    // Ensure the loader has been stopped.  
	    onStopLoading();  
	    // At this point we can release the resources associated with 'mData'.  
	    if (mData != null) {  
	      releaseResources(mData);  
	      mData = null;  
	    }  
	  
	    // The Loader is being reset, so we should stop monitoring for changes.  
	    if(mObserver!=null){
	    	resetObserver(mObserver);
	    	mObserver = null;
	    }
	  }  
	  
	  @Override  
	  public void onCanceled(T data) {  
	    // Attempt to cancel the current asynchronous load.  
	    super.onCanceled(data);  
	  
	    // The load has been canceled, so we should release the resources  
	    // associated with 'data'.  
	    releaseResources(data);  
	  }  
	  
	  private void releaseResources(T data) {  
	    // For a simple List, there is nothing to do. For something like a Cursor, we   
	    // would close it in this method. All resources associated with the Loader  
	    // should be released here.  
	  }  
	   
	  /**
	   * 初始化监听器
	   * @author Lwy
	   * @date 2015-9-8 上午10:23:58
	   */
	  protected abstract K initOberver();
	  /**
	   * 注销监听器
	   * @author Lwy
	   * @date 2015-9-8 上午10:25:45
	   */
	  protected abstract void resetObserver(K observer);


}
