package put.cardy

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import put.cardy.card.AddCardAtivity
import put.cardy.card.CardInfoActivity
import put.cardy.database.CardListAdapter
import put.cardy.database.CardRepository
import put.cardy.model.Card
import put.cardy.transaction.AddTransactionActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.recipe_list_view)

        initListView(listView)
        loadCards(listView)

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTransactionActivity::class.java)
            startActivity(intent)
        }


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
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_add_card) {
            val intent = Intent(this, AddCardAtivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}


/*
* TODO:
* - Widok szczegółowy karty
* - Dodawanie transakcji
* - Notyfikacje
* - usuwanie
* - edycja
* - Logowanie z google?
* */
