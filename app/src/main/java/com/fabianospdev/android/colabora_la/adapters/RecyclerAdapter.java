package com.fabianospdev.android.colabora_la.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fabianospdev.android.colabora_la.R;
import com.fabianospdev.android.colabora_la.model.Feed;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewholderFeeds>
    implements  View.OnClickListener, View.OnTouchListener, View.OnLongClickListener{

  private View.OnClickListener clicklistener;
  private View.OnTouchListener touchListener;
  private View.OnLongClickListener longClickListener;
  public  List<Feed> feeds;
  private Context context;
  private static int toogle = 0;

  public RecyclerAdapter(List<Feed> feeds) {
    this.feeds = feeds;
  }

  @NonNull
  @Override
  public ViewholderFeeds onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.feeds_recycler,parent,false);
    view.setOnClickListener(this);
    view.setOnTouchListener(this);
    view.setOnLongClickListener(this);
    return new ViewholderFeeds(view);
  }


  @Override
  public void onBindViewHolder(@NonNull ViewholderFeeds viewholder, int position) {
    Feed feed = feeds.get ( position );
    viewholder.bind(feed);

  }

  /**  Classe interna Viewholder */
  public static class ViewholderFeeds extends RecyclerView.ViewHolder {
    TextView id;
    TextView title;
    TextView pictureUri;
    ImageView picture;
    TextView message;
    TextView createdAt;
    TextView updatedAt;
    ImageView btnVery_satisfied;
    ImageView btnSatisfied;
    ImageView btnSatisfie_alt;
    ImageView btnDissatisfied;
    ImageView btnVery_dissatisfied;
    Menu menuViewHolder;

    public ViewholderFeeds(@NonNull View view) {
      super ( view );
      id = view.findViewById ( R.id.id );
      title = view.findViewById ( R.id.title );
      pictureUri = view.findViewById ( R.id.pictureUri );
      picture = view.findViewById ( R.id.picture );
      message = view.findViewById ( R.id.message );
      createdAt = view.findViewById ( R.id.createAt );
      updatedAt = view.findViewById ( R.id.updatedAt );

      btnVery_satisfied = view.findViewById ( R.id.btnReactionSentiment_very_satisfied);
      btnSatisfied = view.findViewById ( R.id.btnReactionSentiment_satisfied);
      btnSatisfie_alt = view.findViewById ( R.id.btnReactionSentiment_satisfied_alt);
      btnDissatisfied = view.findViewById ( R.id.btnReactionSentiment_dissatisfied);
      btnVery_dissatisfied = view.findViewById ( R.id.btnReactionSentiment_very_dissatisfied);
    }

    public void bind(Feed feed){
      if(feed != null) {
        id.setId ( feed.getId ( ) );
        title.setText ( feed.getTitle ( ) );
        pictureUri.setText ( feed.getPicture ( ) );
        message.setText ( feed.getMessage ( ) );
        createdAt.setText ( feed.getCreatedAt ( ) );
        updatedAt.setText ( feed.getUpdatedAt ( ) );
        Glide.with ( itemView.getContext ( ) ).load ( feed.getPicture ( ) ).into ( picture );

        itemView.findViewById ( R.id.btnReactionSentiment_very_satisfied ).setOnClickListener ( new View.OnClickListener ( ) {
          @Override
          public void onClick ( View v ) {
            toogleButtom();
            if(toogle == 0) {
              Toast.makeText ( itemView.getContext ( ) , "very_satisfied" , Toast.LENGTH_LONG ).show ( );
            }
          }
        } );

        itemView.findViewById ( R.id.btnReactionSentiment_satisfied ).setOnClickListener ( new View.OnClickListener ( ) {
          @Override
          public void onClick ( View v ) {
            toogleButtom();
            Toast.makeText ( itemView.getContext ( ), "satisfied", Toast.LENGTH_LONG ).show ();
          }
        } );

        itemView.findViewById ( R.id.btnReactionSentiment_satisfied_alt ).setOnClickListener ( new View.OnClickListener ( ) {
          @Override
          public void onClick ( View v ) {
            toogleButtom();
            Toast.makeText ( itemView.getContext ( ), "satisfied_alt", Toast.LENGTH_LONG ).show ();
          }
        } );

        itemView.findViewById ( R.id.btnReactionSentiment_dissatisfied ).setOnClickListener ( new View.OnClickListener ( ) {
          @Override
          public void onClick ( View v ) {
            toogleButtom();
            Toast.makeText ( itemView.getContext ( ), "dissatisfied", Toast.LENGTH_LONG ).show ();
          }
        } );

        itemView.findViewById ( R.id.btnReactionSentiment_very_dissatisfied ).setOnClickListener ( new View.OnClickListener ( ) {
          @Override
          public void onClick ( View v ) {
            toogleButtom();
            Toast.makeText ( itemView.getContext ( ), "very_dissatisfied", Toast.LENGTH_LONG ).show ();
          }
        } );
      }
    }

    private void toogleButtom(){
      try {
        if (btnSatisfie_alt.getVisibility() != View.VISIBLE ){
          btnSatisfied.setVisibility ( View.VISIBLE );
          btnSatisfie_alt.setVisibility ( View.VISIBLE );
          btnDissatisfied.setVisibility ( View.VISIBLE );
          btnVery_dissatisfied.setVisibility ( View.VISIBLE );
          toogle = 1;

        }else {
          btnSatisfied.setVisibility ( View.INVISIBLE );
          btnSatisfie_alt.setVisibility ( View.INVISIBLE );
          btnDissatisfied.setVisibility ( View.INVISIBLE );
          btnVery_dissatisfied.setVisibility ( View.INVISIBLE);
          toogle = 0;
        }
      } catch ( Exception e ) {
        e.printStackTrace ( );
      }
    }
  }
// Fim viewholder


  @Override
  public int getItemCount() {
    return feeds.size();
  }

  public void setOnClickListener(View.OnClickListener clicklistener){
    this.clicklistener = clicklistener;
  }
  public void setOnTouchListener(View.OnTouchListener touchListener){
    this.touchListener = touchListener;
  }
  public void setOnLongClickListener(View.OnLongClickListener longClickListener){
    this.longClickListener = longClickListener;
  }

  @Override
  public void onClick(View v) {
    if(clicklistener != null){
      clicklistener.onClick(v);
    }
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    if(touchListener != null){
      touchListener.onTouch(v, event);
    }
    return false;
  }

  @Override
  public boolean onLongClick(View v) {
    if(longClickListener != null){
      longClickListener.onLongClick(v);
    }
    return false;
  }

  public void removeItem(int position) {
    feeds.remove(position);
    notifyItemRemoved(position);
  }

  public void restoreItem( Feed item, int position) {
    feeds.add(position, item);
    notifyItemInserted(position);
  }

  public List<Feed> getData() {
    return feeds;
  }

  public void onCreateContextMenu( ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    menu.setHeaderTitle("Select The Action");
    menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
    menu.add(0, v.getId(), 0, "SMS");

  }
}