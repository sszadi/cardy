package put.cardy

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import net.danlew.android.joda.JodaTimeAndroid
import put.cardy.card.AddCardActivity
import put.cardy.card.CardInfoActivity
import put.cardy.database.CardListAdapter
import put.cardy.database.CardRepository
import put.cardy.model.Card


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        setContentView(R.layout.list_activity)
        val listView = findViewById<ListView>(R.id.list_view)

        initListView(listView)
        loadCards(listView)

    }

    private fun initListView(listView: ListView) {
        listView.emptyView = findViewById<TextView>(R.id.empty)
        listView.setOnItemClickListener { _, _, position, _ ->
            val currentCard = listView.adapter.getItem(position) as Card
            val intent = Intent(this, CardInfoActivity::class.java)
            intent.putExtra("id", currentCard.id)
            this.startActivity(intent)
        }

    }

    private fun loadCards(listView: ListView) {
        val cards = CardRepository(this).findAll()
        val notesListAdapter = CardListAdapter(this, cards)
        notesListAdapter.notifyDataSetChanged()
        listView.adapter = notesListAdapter
        addDeleteListener()
    }

    private fun addDeleteListener() {
        val deleteButton = findViewById<ImageView>(R.id.delete)
        deleteButton.setOnClickListener(
            
        )

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_add_card) {
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}


/*
* TODO:
* - Dodawanie transakcji
* - Lista transakcji
* - usuwanie transakcji
*  - menu
* - Notyfikacje
* - usuwanie i edycja karty
* - usuwanie transakcji
* - dodać scrolle
* */
