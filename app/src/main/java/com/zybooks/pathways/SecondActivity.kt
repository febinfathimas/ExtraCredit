package com.zybooks.pathways

import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity()  {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ItemAdapter()

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = LinearLayoutManager(this)

        val service = ItemService.create()

        lifecycleScope.launch {
            try {
                val response = service.getItemsAsync().await()
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        adapter.setItems(items)
                    } else {
                        showError("Empty response")
                    }
                } else {
                    showError("Request failed: ${response.code()}")
                }
            } catch (e: Exception) {
                showError("Error: ${e.message}")
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}