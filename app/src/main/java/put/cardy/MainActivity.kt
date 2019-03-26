package put.cardy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import put.cardy.card.AddCardAtivity
import put.cardy.database.CardListAdapter
import put.cardy.database.CardRepository
import put.cardy.model.Card
import put.cardy.transaction.AddTransactionActivity


class MainActivity : BaseActivityWithToolbar() {

    private val cardList: ArrayList<Card> = ArrayList<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCards()

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTransactionActivity::class.java)
            startActivity(intent)
        }


    }

    private fun loadCards() {
        val listView = findViewById<ListView>(R.id.recipe_list_view)
        val cards = CardRepository(this).findAll()
        val notesListAdapter = CardListAdapter(this, cards)
        listView.adapter = notesListAdapter
        notesListAdapter.notifyDataSetChanged()
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
* 1. Powrót na listę po zapisie
* 2. Info, gdy brak kart
* 3. Widok szczegółowy karty
* 4. Dodawanie transakcji
* 5. Notyfikacje
* 6. Logowanie z google?
* */
