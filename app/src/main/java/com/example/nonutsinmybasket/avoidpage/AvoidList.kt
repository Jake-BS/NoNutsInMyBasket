package com.example.nonutsinmybasket.avoidpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nonutsinmybasket.*
import com.example.nonutsinmybasket.models.Diet
import com.example.nonutsinmybasket.models.Ingredient
import com.google.firebase.firestore.FirebaseFirestore

class AvoidList : AppCompatActivity() {

    private lateinit var avoidListAdapter: AvoidListAdapter
    private lateinit var dietListAdapter: DietListAdapter
    private lateinit var db: FirebaseFirestore
    private var isLogin: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avoid_list)

        isLogin = intent.getStringExtra("email_id")
        db = FirebaseFirestore.getInstance()

        val actionBar = supportActionBar

        actionBar!!.title = "Avoid List"
        //actionBar.setDisplayHomeAsUpEnabled(true)

        settingUpAvoidList()


    }

    override fun onBackPressed() {
        val isLogin = intent.getStringExtra("email_id")
        val currentIngredients = ingredientToText(avoidListAdapter.getIngredients())
        val currentDiets = dietToText(dietListAdapter.getDiets())
        val updatedUserData = hashMapOf(
            "custom_ingredients" to currentIngredients,
            "diets" to currentDiets
        )
        if (isLogin != null) {
            db.collection("USERS").document(isLogin).update(updatedUserData as Map<String, Any>)
                .addOnSuccessListener { Toast.makeText(this,
                    "Updated avoid list",
                    Toast.LENGTH_LONG).show()}
                .addOnFailureListener{ e->
                    Toast.makeText(this,
                        "Didn't update avoid list, $e",
                        Toast.LENGTH_LONG).show()
                }
        }
        super.onBackPressed()
    }

    fun ingredientToText(ingredientsList : MutableList<Ingredient>): ArrayList<String> {
        val stringList = arrayListOf<String>()
        for (ingredient in ingredientsList) stringList.add(ingredient.name)
        return stringList
    }

    fun dietToText(dietsList: MutableList<Diet>) : ArrayList<String> {
        val stringList = arrayListOf<String>()
        for (diet in dietsList) {
            if (diet.isChecked) stringList.add(diet.name)
        }
        return stringList
    }

    fun textToIngredient(textIngredients: ArrayList<String>) : MutableList<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        for (textIngredient in textIngredients) ingredients.add(Ingredient(textIngredient))
        return ingredients
    }


    fun settingUpAvoidList(){
        avoidListAdapter = AvoidListAdapter(mutableListOf())
        if (isLogin != null) {
            val user = db.collection("USERS").document(isLogin!!)
            user.get().addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    val document = task.result
                    val ingredientsText = document?.get("custom_ingredients")
                    avoidListAdapter.setIngredientsList(textToIngredient(ingredientsText as ArrayList<String>))
                    var rvAvoidList = findViewById<RecyclerView>(R.id.rvAvoidList)
                    rvAvoidList.adapter = avoidListAdapter
                    rvAvoidList.layoutManager = LinearLayoutManager(this)
                    var btnAddIngredient = findViewById<Button>(R.id.btnAdd)
                    var etEnterIngredient = findViewById<EditText>(R.id.etEnterIngredient)
                    etEnterIngredient.setOnKeyListener(View.OnKeyListener {v, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                            addToAvoidList(etEnterIngredient)
                            true
                        }
                        false
                    })
                    btnAddIngredient.setOnClickListener {
                        addToAvoidList(etEnterIngredient)
                    }
                    settingUpDiets(document.get("diets") as ArrayList<String>)
                }
            }
        }
    }

    fun settingUpDiets(selectedDiets: ArrayList<String>) {
        dietListAdapter = DietListAdapter(mutableListOf(), this.avoidListAdapter)
        var rvDietList = findViewById<RecyclerView>(R.id.rvDietsList)
        rvDietList.adapter = dietListAdapter
        rvDietList.layoutManager = LinearLayoutManager(this)
        val diets = getDiets()
        for (diet in diets) {
            for (sDiet in selectedDiets) {
                if (diet.name == sDiet) diet.isChecked = true
            }
        }
        for(diet in diets) dietListAdapter.addDiet(diet)
    }

    fun getDiets() : List<Diet>{
        //val webDiets = db.collection("DIETS")
        val notVeganList = listOf<String>("Zinc Caseinate", "Yogurt", "Whole Milk", "Whipped Topping", "Whipped Cream", "Whey Protein Hydrolysate", "Whey Protein Concentrate", "Whey Powder", "Whey", "Sweet Whey", "Sweetened Condensed Milk", "Sour Milk Solids", "Sour Cream", "Sodium Caseinate", "Skim Milk", "Sheep Milk Cheese", "Sheep Milk", "Rennet Casein", "Recaldent", "Pudding", "Potassium Caseinate", "Paneer", "Nougat", "Nonfat Milk", "Natural Butter Flavor", "Milk Solids", "Milk Protein", "Milk Powder", "Milk Fat", "Milk Derivative", "Milk", "Malted Milk", "Magnesium Caseinate", "Low-Fat Milk", "Lactulose", "Lactose", "Lactoglobulin", "Lactoferrin", "Lactalbumin", "Iron Caseinate", "Hydrolyzed Milk Protein", "Hydrolyzed Casein", "Half & Half", "Goat Milk", "Goat Cheese", "Ghee", "Evaporated Milk", "Dry Milk Solids", "Dry Milk Powder", "Demineralized Whey", "Delactosed Whey", "Custard", "Curds", "Cream", "Cottage Cheese", "Condensed Milk", "Cheese (All animal-based)", "Caseinate (in general)", "Casein", "Calcium Caseinate", "Buttermilk Powder", "Buttermilk", "Butter Solids", "Butter Oil", "Butter Fat", "Butter Esters", "Butter", "Ammonium Caseinate", "Acidophilus Milk", "Wool Wax", "Wool Fat", "Wool", "Whey", "Wax", "Vitamin H", "Ergocalciferol", "Vitamin D", "Vitamin", "Vitamin B Factor", "Vitamin B-Complex Factor", "Vitamin A", "Uric Acid", "Carbamide", "Urea", "Tyrosine", "Sea Turtle Oil", "Turtle Oil", "Triterpene Alcohols", "Tallow Imidazoline", "Tallow Glycerides", "Talloweth-6", "Tallow Amine", "Tallow Amide", "Tallow Acid", "Stearic Acid", "Tallow Fatty Alcohol", "Tallow", "Suede", "Sterols", "Steroids", "Stearyl Stearate", "Stearyl Octanoate", "Stearyl Imidazoline", "Stearyl Heptanoate", "Stearyl Glycyrrhetinate", "Stearyldimethyl Amine", "Stearyl Citrate", "Stearyl Caprylate", "Stearyl Betaine", "Sterols", "Stearyl Alcohol", "Stearyl Acetate", "Stearoyl Lactylic Acid", "Stearoxytrimethylsilane", "Stearone", "Stearic Hydrazide", "Stearic Acid", "Stearates", "Stearamine Oxide", "Stearamine", "Stearamide", "Squalene", "Squalane", "Sponge", "Sperm Oil", "Cetyl Palmitate", "Spermaceti", "Sodium Tallowate", "Sodium Steroyl Lactylate", "Sodium Caseinate", "Snails", "Silk Powder", "Silk", "Resinous Glaze", "Shellac", "Sheepskin", "Shark Liver Oil", "Sea Turtle Oil", "Sable Brushes", "Royal Jelly", "RNA. Ribonucleic Acid", "Ribonucleic Acid", "Retinol", "Resinous Glaze", "Rennin", "Rennet. Rennin", "Provitamin D-2", "Provitamin B-5", "Provitamin A", "Propolis", "Progesterone", "Pristane", "Polysorbates", "Polypeptides", "Polyglycerol", "Placenta Polypeptides Protein", "Placenta", "Pepsin", "Panthenyl", "Provitamin B-5", "Vitamin B-Complex Factor", "Dexpanthenol", "Panthenol", "Palmitic Acid", "Palmitate", "Palmitamine", "Palmitamide", "Oleyl Stearate", "Oleyl Oleate", "Oleyl Myristate", "Oleyl Imidazoline", "Oleyl Arachidate", "Ocenol", "Oleyl Alcohol", "Oleths", "Oils", "Oleic Acid", "Octyl Dodecanol", "Ocenol", "Nucleic Acids", "Myristyls", "Myristic Acid", "Myristal Ether Sulfate", "Musk (Oil)", "Glycerides", "Monoglycerides", "Mink Oil", "Milk Protein", "Milk", "Methionine", "Marine Oil", "Lipids", "Lipoids", "Lipids", "Lipase", "Linoleic Acid", "Choline Bitartrate", "Lecithin", "Other Types of Skin", "Alligator Skin", "Calfskin, \nSheepskin", "Suede", "Leather", "Lard", "Lanosterols", "Lanolin Alcohol", "Wool Wax", "Wool Fat", "Lanolin Acids", "Lanolin", "Lanogene", "Laneth", "Lactose", "Lactic Acid", "Keratin", "Isopropyl Palmitate", "Isopropyl Myristate", "Isopropyl Lanolate", "Isinglass", "Insulin", "Imidazolidinyl Urea", "Hydrolyzed Animal Protein", "Hydrocortisone", "Hyaluronic Acid", "Horsehair", "Honeycomb", "Honey", "Hide Glue", "Pearl Essence", "Guanine", "Glycreth-26", "Glyceryls", "Glycerol", "Glycerin. Glycerol", "Glycerides", "Glucose Tyrosinase", "Gel", "Gelatin", "Gel", "Fur", "Fish Scales", "Fish Oil", "Fish Liver Oil", "Feathers", "FD&C Colors", "Fatty Acids", "Fats", "Estradiol", "Estrogen", "Estradiol", "Ergosterol", "Ergocalciferol", "Emu Oil", "Elastin", "Egg Protein", "Dyes", "Duodenum Substances", "Down", "Dimethyl Stearamine", "Diglycerides", "Dexpanthenol", "Cystine", "L-Form", "Cysteine", "Corticosteroid", "Cortisone", "Corticosteroid", "Dyes", "Colors", "Collagen", "Cod Liver Oil", "Cochineal", "Civet", "Choline Bitartrate", "Cholesterol", "Cholesterin", "Chitosan", "Cetyl Palmitate", "Cetyl Alcohol", "Cerebrosides", "Cera Flava", "Catgut", "Castoreum", "Castor", "Cashmere", "Caseinate", "Sodium Caseinate", "Caseinate", "Casein", "Beta Carotene", "Carotene, \nProvitamin A", "Carminic Acid", "Carminic Acid", "Cochineal", "Carmine", "Carbamide", "Caprylic Triglyceride", "Caprylic Acid", "Capryl Betaine", "Caprylamine Oxide", "Calfskin", "Calciferol", "Bone Meal", "Bone Char", "Boar Bristles", "Blood", "Vitamin B Factor", "Vitamin H", "Biotin", "Honeycomb", "Beeswax", "Bee Products", "Bee Pollen", "Arachidyl Proprionate", "Arachidonic Acid", "Animal Hair", "Animal Fats and Oils", "Angora", "Aminosuccinate Acid", "Amino Acids", "Ambergris", "Alpha-Hydroxy Acids", "Alligator Skin", "Allantoin", "Aliphatic Alcohol", "Aldioxa", "Alcloxa", "Albumin", "Albumen", "Alanine", "Adrenaline", "Zebra", "Zaza-Mushi (Stonefly Larvae)", "Yellowfin Tuna", "Yellow Jacket", "Yak", "Xia Shrimp", "Wtchetty Grubs", "Worms", "Whiting", "Whitefish Roe", "Whitefish", "Whale Meat", "Wasps", "Walrus", "Viper", "Venison", "Urchin Roe", "Urchin", "Turtle Eggs", "Turtle", "Turkey Eggs", "Turkey", "Tuna", "Trout", "Toad", "Tilapia", "Tarantula", "Swordfish", "Swan Egg", "Swan", "Squirrel", "Squid", "Spider", "Snake Egg", "Snake", "Snail", "Smelt Cavier", "Smelt", "Shrimp", "Shark", "Sea Urchin", "Sea Trout", "Sea Lion", "Sea Cucumber", "Sea Bass", "Seal Meat", "Scorpion", "Scallops", "Salmon", "Sago Palm Weevil Larvae", "Sago Grubs", "Roe", "Rhea Egg", "Rhea", "Rattlesnake", "Rat Meat", "Raccoon", "Rabbit", "Quail Eggs", "Quail", "Python", "Ptarmigan", "Prawns", "Pork", "Pigeon", "Partridge Egg", "Partridge", "Oyster", "Oxen", "Ostrich Egg", "Ostrich", "Opossum", "Octopus", "Mutton", "Mussels", "Musk Ox", "Mullet Roe", "Mullet", "Mouse", "Moose Meat", "Merling", "Lobster Roe", "Lobster", "Llama Meat", "Lizard Eggs", "Lizard", "Liver", "Leech", "Lamb", "Kidney", "Kangaroo", "Jellyfish", "Iguana", "Ibex", "Horse Meat", "Herring", "Heart", "Hare", "Guinea Fowl Egg", "Guinea Fowl", "Guanaco", "Grouper", "Grasshopper", "Gopher", "Goose Egg", "Goose", "Gnu", "Frog Eggs", "Frog", "Flounder", "Fish", "Escargots", "Escamole (Ant Larvae)", "Emu Egg", "Emu", "Elk", "Eggs", "Eel", "Duck Eggs", "Duck", "Donkey Meat", "Dog Meat", "Deer", "Crocodile", "Crickets", "Crab Roe", "Crab", "Coconut Grubs", "Clam", "Chicken Eggs", "Chicken", "Caviar", "Catfish", "Caterpillar", "Cat Meat", "Carp", "Bugs", "Buffalo", "Brain", "Bison", "Bird Egg", "Beluga Caviar", "Beetle", "Beef", "Bee Larva", "Bee", "Bear Meat", "Bass", "Antelope Meat", "Ant", "Amphibian Egg", "Alpaca Meat", "Alligator", "Albacore", "Ahi Tuna", "African Buffalo", "Abalone", "Aardvark")
        //val veganDietForWeb = hashMapOf(
        //    "ingredients" to notVeganList
        //)
        //webDiets.document("VEGAN").set(veganDietForWeb)
        val veganDiet = Diet(notVeganList, "Vegan", false)
        val nonVeggieList = listOf<String>("Zebra", "Zaza-Mushi (Stonefly Larvae)", "Yellowfin Tuna", "Yellow Jacket", "Yak", "Xia Shrimp", "Wtchetty Grubs", "Worms", "Whiting", "Whitefish Roe", "Whitefish", "Whale Meat", "Wasps", "Walrus", "Viper", "Venison", "Urchin Roe", "Urchin", "Turtle", "Turkey", "Tuna", "Trout", "Toad", "Tilapia", "Tarantula", "Swordfish", "Swan", "Squirrel", "Squid", "Spider", "Snake", "Snail", "Smelt Cavier", "Smelt", "Shrimp", "Shark", "Sea Urchin", "Sea Trout", "Sea Lion", "Sea Cucumber", "Sea Bass", "Seal Meat", "Scorpion", "Scallops", "Salmon", "Sago Palm Weevil Larvae", "Sago Grubs", "Roe", "Rhea", "Rattlesnake", "Rat Meat", "Raccoon", "Rabbit", "Quail", "Python", "Ptarmigan", "Prawns", "Pork", "Pigeon", "Partridge", "Oyster", "Oxen", "Ostrich", "Opossum", "Octopus", "Mutton", "Mussels", "Musk Ox", "Mullet Roe", "Mullet", "Mouse", "Moose Meat", "Merling", "Lobster Roe", "Lobster", "Llama Meat", "Lizard", "Liver", "Leech", "Lamb", "Kidney", "Kangaroo", "Jellyfish", "Iguana", "Ibex", "Horse Meat", "Herring", "Heart", "Hare", "Guinea Fowl", "Guanaco", "Grouper", "Grasshopper", "Gopher", "Goose", "Gnu", "Frog", "Flounder", "Fish", "Escargots", "Escamole (Ant Larvae)", "Emu", "Elk", "Eel", "Duck", "Donkey Meat", "Dog Meat", "Deer", "Crocodile", "Crickets", "Crab Roe", "Crab", "Coconut Grubs", "Clam", "Chicken", "Caviar", "Catfish", "Caterpillar", "Cat Meat", "Carp", "Bugs", "Buffalo", "Brain", "Bison", "Beluga Caviar", "Beetle", "Beef", "Bee Larva", "Bee", "Bear Meat", "Bass", "Antelope Meat", "Ant", "Alpaca Meat", "Alligator", "Albacore", "Ahi Tuna", "African Buffalo", "Abalone", "Aardvark")
        //val veggieDietForWeb = hashMapOf(
        //    "ingredients" to nonVeggieList
        //)
        //webDiets.document("VEGETARIAN").set(veggieDietForWeb)
        val veggieDiet = Diet(nonVeggieList, "Vegetarian", false)
        val diets = listOf<Diet>(veganDiet, veggieDiet)
        return diets
    }

    fun addToAvoidList(etEnterIngredient: EditText)
    {
        val ingredientName = etEnterIngredient.text.toString()
        if(ingredientName.isNotEmpty()) {
            val ingredient = Ingredient(ingredientName)
            avoidListAdapter.addIngredient(ingredient)
            etEnterIngredient.text.clear()
        }
    }
}
