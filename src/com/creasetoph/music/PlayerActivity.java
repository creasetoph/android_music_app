package com.creasetoph.music;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayerActivity extends Activity {
    
    private static Map<Integer,String> buttonMap = new HashMap<Integer,String>();
    static {
        buttonMap.put(R.id.play_pause_button,"onPlayPauseClick");
        buttonMap.put(R.id.stop_button      ,"onStopClick");
        buttonMap.put(R.id.prev_button      ,"onPrevClick");
        buttonMap.put(R.id.next_button      ,"onNextClick");
    }
    
    private PlayerController _controller;
    
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        _controller = PlayerController.getInstance();
        setContentView(R.layout.player_view);
        attachButtonListeners();
        setPlayPause();
    }
    
    private void attachButtonListeners() {
        for(int id: buttonMap.keySet()) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    buttonClickDispatch(v);
                }
            });
        }
    }
    
    private void buttonClickDispatch(View view) {
        try {
            Class<?> params[] = new Class[1];
            params[0] = View.class;
            Method method = getClass().getDeclaredMethod(buttonMap.get(view.getId()),params);
            method.invoke(this,view);
        }catch(Exception e) {
            Logger.log(e.getMessage());
        }
    }
    
    private void onPlayPauseClick(View view) {
        Logger.log("onPlayPause");
        _controller.playPause();
        setPlayPause();
    }
    
    private void setPlayPause() {
        Button button = (Button) findViewById(R.id.play_pause_button);
        if(_controller.isPlaying()) {
            button.setText(R.string.pause);
        }else {
            button.setText(R.string.play);
        } 
    }
    
    private void onStopClick(View view) {
        Logger.log("onStop");
        _controller.stop();
        setPlayPause();
    }
    
    private void onPrevClick(View view) {
        Logger.log("onPrev");
        _controller.prev();
    }
    
    private void onNextClick(View view) {
        Logger.log("onNext");
        _controller.next();
    }
}