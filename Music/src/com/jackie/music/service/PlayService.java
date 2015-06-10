package com.jackie.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;

import com.jackie.music.constant.MusicConstant;
import com.lidroid.xutils.util.LogUtils;

/**
 * 
 * @author jackie
 *
 */
public class PlayService extends Service{

	//ý�岥��������
	private MediaPlayer mediaPlayer =  new MediaPlayer();
	 
	//�����ļ�·��
	private String path;
	 
	//��ͣ״̬
	private boolean isPause = false;  
	 
	 
	/* 
	 * ������¼�
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override  
    public int onStartCommand(Intent intent, int flags, int startId) {  
		
       path = intent.getStringExtra("url");          
       int msg = intent.getIntExtra("MSG", 0);  
       
       if(msg == MusicConstant.PLAY_MSG) {  
       	
	       LogUtils.i("start");
	       play(0);  
          
       } else if(msg == MusicConstant.PAUSE_MSG) {
        	
    	   LogUtils.i("pause");
    	   pause();
            
        } else if(msg == MusicConstant.STOP_MSG) {
        	
        	LogUtils.i("stop");
            stop();
            
        }  
        return super.onStartCommand(intent, flags, startId);  
    }  

	
	/** 
     * �������� 
     * @param position 
     */  
    private void play(int position) {  
        try {  
            mediaPlayer.reset();//�Ѹ�������ָ�����ʼ״̬  
            mediaPlayer.setDataSource(path);  
            mediaPlayer.prepare();  //���л���  
            mediaPlayer.setOnPreparedListener(new PreparedListener(position));//ע��һ��������  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * ��ͣ���� 
     */  
    private void pause() {  
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {  
            
        	mediaPlayer.pause();  
            isPause = true;  
        }else{
        	mediaPlayer.start();
        }
    }  
    
    /** 
     * ֹͣ���� 
     */  
    private void stop(){  
        if(mediaPlayer != null) {  
            mediaPlayer.stop();  
            
           /* try {  
                mediaPlayer.prepare(); // �ڵ���stop�������Ҫ�ٴ�ͨ��start���в���,��Ҫ֮ǰ����prepare����  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  */
        }  
    }  
    
    @Override  
    public void onDestroy() {  
        if(mediaPlayer != null){  
            mediaPlayer.stop();  
            mediaPlayer.release();  
        }  
    }  
    
    /** 
     *  
     * ʵ��һ��OnPrepareLister�ӿ�,������׼���õ�ʱ��ʼ���� 
     * 
     */  
    private final class PreparedListener implements OnPreparedListener {  
        private int positon;  
          
        public PreparedListener(int positon) {  
            this.positon = positon;  
        }  
          
        @Override  
        public void onPrepared(MediaPlayer mp) {  
            mediaPlayer.start();    //��ʼ����  
            if(positon > 0) {    //������ֲ��Ǵ�ͷ����  
                mediaPlayer.seekTo(positon);  
            }  
        }  
    }  
}
