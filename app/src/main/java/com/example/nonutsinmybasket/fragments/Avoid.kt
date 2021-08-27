package com.example.nonutsinmybasket.fragments

import androidx.annotation.RequiresApi
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nonutsinmybasket.R
import com.example.nonutsinmybasket.avoidpage.AvoidListAdapter
import com.example.nonutsinmybasket.avoidpage.DietListAdapter
import com.example.nonutsinmybasket.models.Diet
import com.example.nonutsinmybasket.models.Ingredient
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_avoid.view.*

class Avoid(var userId: String?) : Fragment() {
    private val btnToggleDark: Switch? = null
    private var avoidListAdapter: AvoidListAdapter? = null
    private var dietListAdapter: DietListAdapter? = null
    private lateinit var  db: FirebaseFirestore

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view  = inflater.inflate(R.layout.fragment_avoid, container, false)
        db = FirebaseFirestore.getInstance()
        settingUpAvoidList(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
    private fun ingredientToText(ingredientsList : MutableList<Ingredient>): ArrayList<String> {
        val stringList = arrayListOf<String>()
        for (ingredient in ingredientsList) stringList.add(ingredient.name)
        return stringList
    }

    private fun dietToText(dietsList: MutableList<Diet>) : ArrayList<String> {
        val stringList = arrayListOf<String>()
        for (diet in dietsList) {
            if (diet.isChecked) stringList.add(diet.name)
        }
        return stringList
    }

    private fun textToIngredient(textIngredients: ArrayList<String>) : MutableList<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        for (textIngredient in textIngredients) ingredients.add(Ingredient(textIngredient))
        return ingredients
    }


    private fun settingUpAvoidList(view: View) {
        avoidListAdapter = AvoidListAdapter(mutableListOf())
        if (userId != null) {
            val user = db.collection("USERS").document(userId!!)
            user.get().addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    val document = task.result
                    val ingredientsText = document?.get("custom_ingredients")
                    avoidListAdapter!!.setIngredientsList(textToIngredient(ingredientsText as ArrayList<String>))
                    var rvAvoidList = view.rvAvoidList
                    rvAvoidList.adapter = avoidListAdapter
                    rvAvoidList.layoutManager = LinearLayoutManager(activity)
                    var btnAddIngredient = view.btnAdd
                    var etEnterIngredient = view.etEnterIngredient
                    etEnterIngredient.setOnKeyListener{ _, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                            addToAvoidList(etEnterIngredient)
                            true
                        }
                        false
                    }
                    btnAddIngredient.setOnClickListener {
                        addToAvoidList(etEnterIngredient)
                    }
                    settingUpDiets(document.get("diets") as ArrayList<String>, view)
                }
                else {
                    Toast.makeText(activity,
                        "Failed to get data from firestore",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
        else {
            Toast.makeText(activity,
                "Null id",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun settingUpDiets(selectedDiets: ArrayList<String>, view: View) {
        dietListAdapter = this.avoidListAdapter?.let { DietListAdapter(mutableListOf(), it) }
        var rvDietList = view.rvDietsList
        rvDietList.adapter = dietListAdapter
        rvDietList.layoutManager = LinearLayoutManager(activity)
        val diets = getDiets()
        for (diet in diets) {
            for (sDiet in selectedDiets) {
                if (diet.name == sDiet) diet.isChecked = true
            }
        }
        for(diet in diets) dietListAdapter?.addDiet(diet)
    }

    private fun getDiets() : List<Diet>{
        val notVeganList = listOf<String>("Zinc Caseinate", "Zebra", "Zaza-Mushi (Stonefly Larvae)", "Yogurt", "Yellowfin Tuna", "Yellow Jacket", "Yak", "Xia Shrimp", "Wtchetty Grubs", "Worms", "Wool Wax", "Wool Fat", "Wool", "Whole Milk", "Whiting", "Whitefish Roe", "Whitefish", "Whipped Topping", "Whipped Cream", "Whey Protein Hydrolysate", "Whey Protein Concentrate", "Whey Powder", "Whey", "Whale Meat", "Wax", "Wasps", "Walrus", "Vitamin H", "Vitamin D", "Vitamin B-Complex Factor", "Vitamin B Factor", "Vitamin A", "Vitamin", "Viper", "Venison", "Uric Acid", "Urea", "Urchin Roe", "Urchin", "Tyrosine", "Turtle Oil", "Turtle Eggs", "Turtle", "Turkey Eggs", "Turkey", "Tuna", "Trout", "Triterpene Alcohols", "Toad", "Tilapia", "Tarantula", "Talloweth-6", "Tallow Imidazoline", "Tallow Glycerides", "Tallow Fatty Alcohol", "Tallow Amine", "Tallow Amide", "Tallow Acid", "Tallow", "Swordfish", "Sweetened Condensed Milk", "Sweet Whey", "Swan Egg", "Swan", "Suede", "Sterols", "Steroids", "Stearyldimethyl Amine", "Stearyl Stearate", "Stearyl Octanoate", "Stearyl Imidazoline", "Stearyl Heptanoate", "Stearyl Glycyrrhetinate", "Stearyl Citrate", "Stearyl Caprylate", "Stearyl Betaine", "Stearyl Alcohol", "Stearyl Acetate", "Stearoyl Lactylic Acid", "Stearoxytrimethylsilane", "Stearone", "Stearic Hydrazide", "Stearic Acid", "Stearates", "Stearamine Oxide", "Stearamine", "Stearamide", "Squirrel", "Squid", "Squalene", "Squalane", "Sponge", "Spider", "Spermaceti", "Sperm Oil", "Sour Milk Solids", "Sour Cream", "Sodium Tallowate", "Sodium Steroyl Lactylate", "Sodium Caseinate", "Snake Egg", "Snake", "Snails", "Snail", "Smelt Cavier", "Smelt", "Skim Milk", "Silk Powder", "Silk", "Shrimp", "Shellac", "Sheepskin", "Sheep Milk Cheese", "Sheep Milk", "Shark Liver Oil", "Shark", "Seal Meat", "Sea Urchin", "Sea Turtle Oil", "Sea Trout", "Sea Lion", "Sea Cucumber", "Sea Bass", "Scorpion", "Scallops", "Salmon", "Sago Palm Weevil Larvae", "Sago Grubs", "Sable Brushes", "Royal Jelly", "Roe", "Ribonucleic Acid", "Rhea Egg", "Rhea", "Retinol", "Resinous Glaze", "Rennin", "Rennet. Rennin", "Rennet Casein", "Recaldent", "Rattlesnake", "Rat Meat", "Raccoon", "Rabbit", "RNA. Ribonucleic Acid", "Quail Eggs", "Quail", "Python", "Pudding", "Ptarmigan", "Provitamin D-2", "Provitamin B-5", "Provitamin A", "Propolis", "Progesterone", "Pristane", "Prawns", "Potassium Caseinate", "Pork", "Polysorbates", "Polypeptides", "Polyglycerol", "Placenta Polypeptides Protein", "Placenta", "Pigeon", "Pepsin", "Pearl Essence", "Partridge Egg", "Partridge", "Panthenyl", "Panthenol", "Paneer", "Palmitic Acid", "Palmitate", "Palmitamine", "Palmitamide", "Oyster", "Oxen", "Other Types of Skin", "Ostrich Egg", "Ostrich", "Opossum", "Oleyl Stearate", "Oleyl Oleate", "Oleyl Myristate", "Oleyl Imidazoline", "Oleyl Arachidate", "Oleyl Alcohol", "Oleths", "Oleic Acid", "Oils", "Octyl Dodecanol", "Octopus", "Ocenol", "Nucleic Acids", "Nougat", "Nonfat Milk", "Natural Butter Flavor", "Myristyls", "Myristic Acid", "Myristal Ether Sulfate", "Mutton", "Mussels", "Musk Ox", "Musk (Oil)", "Mullet Roe", "Mullet", "Mouse", "Moose Meat", "Monoglycerides", "Mink Oil", "Milk Solids", "Milk Protein", "Milk Powder", "Milk Fat", "Milk Derivative", "Milk", "Methionine", "Merling", "Marine Oil", "Malted Milk", "Magnesium Caseinate", "Low-Fat Milk", "Lobster Roe", "Lobster", "Llama Meat", "Lizard Eggs", "Lizard", "Liver", "Lipoids", "Lipids", "Lipase", "Linoleic Acid", "Leech", "Lecithin", "Leather", "Lard", "Lanosterols", "Lanolin Alcohol", "Lanolin Acids", "Lanolin", "Lanogene", "Laneth", "Lamb", "Lactulose", "Lactose", "Lactoglobulin", "Lactoferrin", "Lactic Acid", "Lactalbumin", "L-Form", "Kidney", "Keratin", "Kangaroo", "Jellyfish", "Isopropyl Palmitate", "Isopropyl Myristate", "Isopropyl Lanolate", "Isinglass", "Iron Caseinate", "Insulin", "Imidazolidinyl Urea", "Iguana", "Ibex", "Hydrolyzed Milk Protein", "Hydrolyzed Casein", "Hydrolyzed Animal Protein", "Hydrocortisone", "Hyaluronic Acid", "Horsehair", "Horse Meat", "Honeycomb", "Honey", "Hide Glue", "Herring", "Heart", "Hare", "Half & Half", "Guinea Fowl Egg", "Guinea Fowl", "Guanine", "Guanaco", "Grouper", "Grasshopper", "Gopher", "Goose Egg", "Goose", "Goat Milk", "Goat Cheese", "Gnu", "Glycreth-26", "Glyceryls", "Glycerol", "Glycerin. Glycerol", "Glycerides", "Glucose Tyrosinase", "Ghee", "Gelatin", "Gel", "Fur", "Frog Eggs", "Frog", "Flounder", "Fish Scales", "Fish Oil", "Fish Liver Oil", "Fish", "Feathers", "Fatty Acids", "Fats", "FD&C Colors", "Evaporated Milk", "Estrogen", "Estradiol", "Escargots", "Escamole (Ant Larvae)", "Ergosterol", "Ergocalciferol", "Emu Oil", "Emu Egg", "Emu", "Elk", "Elastin", "Eggs", "Egg Protein", "Eel", "Dyes", "Duodenum Substances", "Duck Eggs", "Duck", "Dry Milk Solids", "Dry Milk Powder", "Down", "Donkey Meat", "Dog Meat", "Dimethyl Stearamine", "Diglycerides", "Dexpanthenol", "Demineralized Whey", "Delactosed Whey", "Deer", "Cystine", "Cysteine", "Custard", "Curds", "Crocodile", "Crickets", "Cream", "Crab Roe", "Crab", "Cottage Cheese", "Cortisone", "Corticosteroid", "Condensed Milk", "Colors", "Collagen", "Cod Liver Oil", "Coconut Grubs", "Cochineal", "Clam", "Civet", "Choline Bitartrate", "Cholesterol", "Cholesterin", "Chitosan", "Chicken Eggs", "Chicken", "Cheese (All animal-based)", "Cetyl Palmitate", "Cetyl Alcohol", "Cerebrosides", "Cera Flava", "Caviar", "Catgut", "Catfish", "Caterpillar", "Cat Meat", "Castoreum", "Castor", "Cashmere", "Caseinate (in general)", "Caseinate", "Casein", "Carp", "Carotene", "Carminic Acid", "Carmine", "Carbamide", "Caprylic Triglyceride", "Caprylic Acid", "Caprylamine Oxide", "Capryl Betaine", "Calfskin", "Calcium Caseinate", "Calciferol", "Buttermilk Powder", "Buttermilk", "Butter Solids", "Butter Oil", "Butter Fat", "Butter Esters", "Butter", "Bugs", "Buffalo", "Brain", "Bone Meal", "Bone Char", "Boar Bristles", "Blood", "Bison", "Bird Egg", "Biotin", "Beta Carotene", "Beluga Caviar", "Beetle", "Beeswax", "Beef", "Bee Products", "Bee Pollen", "Bee Larva", "Bee", "Bear Meat", "Bass", "Arachidyl Proprionate", "Arachidonic Acid", "Antelope Meat", "Ant", "Animal Hair", "Animal Fats and Oils", "Angora", "Amphibian Egg", "Ammonium Caseinate", "Aminosuccinate Acid", "Amino Acids", "Ambergris", "Alpha-Hydroxy Acids", "Alpaca Meat", "Alligator Skin", "Alligator", "Allantoin", "Aliphatic Alcohol", "Aldioxa", "Alcloxa", "Albumin", "Albumen", "Albacore", "Alanine", "Ahi Tuna", "African Buffalo", "Adrenaline", "Acidophilus Milk", "Abalone", "Aardvark")
        val veganDiet = Diet(notVeganList, "Vegan", false)
        val nonVeggieList = listOf<String>("Zebra", "Zaza-Mushi (Stonefly Larvae)", "Yellowfin Tuna", "Yellow Jacket", "Yak", "Xia Shrimp", "Wtchetty Grubs", "Worms", "Whiting", "Whitefish Roe", "Whitefish", "Whale Meat", "Wasps", "Walrus", "Viper", "Venison", "Urchin Roe", "Urchin", "Turtle", "Turkey", "Tuna", "Trout", "Toad", "Tilapia", "Tarantula", "Swordfish", "Swan", "Squirrel", "Squid", "Spider", "Snake", "Snail", "Smelt Cavier", "Smelt", "Shrimp", "Shark", "Sea Urchin", "Sea Trout", "Sea Lion", "Sea Cucumber", "Sea Bass", "Seal Meat", "Scorpion", "Scallops", "Salmon", "Sago Palm Weevil Larvae", "Sago Grubs", "Roe", "Rhea", "Rattlesnake", "Rat Meat", "Raccoon", "Rabbit", "Quail", "Python", "Ptarmigan", "Prawns", "Pork", "Pigeon", "Partridge", "Oyster", "Oxen", "Ostrich", "Opossum", "Octopus", "Mutton", "Mussels", "Musk Ox", "Mullet Roe", "Mullet", "Mouse", "Moose Meat", "Merling", "Lobster Roe", "Lobster", "Llama Meat", "Lizard", "Liver", "Leech", "Lamb", "Kidney", "Kangaroo", "Jellyfish", "Iguana", "Ibex", "Horse Meat", "Herring", "Heart", "Hare", "Guinea Fowl", "Guanaco", "Grouper", "Grasshopper", "Gopher", "Goose", "Gnu", "Frog", "Flounder", "Fish", "Escargots", "Escamole (Ant Larvae)", "Emu", "Elk", "Eel", "Duck", "Donkey Meat", "Dog Meat", "Deer", "Crocodile", "Crickets", "Crab Roe", "Crab", "Coconut Grubs", "Clam", "Chicken", "Caviar", "Catfish", "Caterpillar", "Cat Meat", "Carp", "Bugs", "Buffalo", "Brain", "Bison", "Beluga Caviar", "Beetle", "Beef", "Bee Larva", "Bee", "Bear Meat", "Bass", "Antelope Meat", "Ant", "Alpaca Meat", "Alligator", "Albacore", "Ahi Tuna", "African Buffalo", "Abalone", "Aardvark")
        val veggieDiet = Diet(nonVeggieList, "Vegetarian", false)
        val nonSugarList = listOf<String>("Yellow sugar", "Turbinado sugar", "Treacle", "Sugar", "Sucrose", "Sucanat", "Sorghum syrup", "Rice syrup", "Refiner's syrup", "Raw sugar", "Panela sugar", "Muscovado sugar", "Molasses", "Maple syrup", "Maltose", "Maltodextrin", "Malt syrup", "Lactose", "Invert sugar", "Icing sugar", "Honey", "High-Fructose Corn Syrup (HFCS)", "Grape sugar", "Golden syrup", "Golden sugar", "Glucose syrup solids", "Glucose", "Galactose", "Fruit juice concentrate", "Fruit juice", "Fructose", "Florida crystals", "Evaporated cane juice", "Ethyl maltol", "Diastatic malt", "Dextrose", "Dextrin", "Demerara sugar", "Date sugar", "Crystalline fructose", "Corn syrup solids", "Corn syrup", "Confectioner's sugar", "Coconut sugar", "Castor sugar", "Carob syrup", "Caramel", "Cane sugar", "Cane juice crystals", "Buttered sugar/buttercream", "Brown sugar", "Brown rice syrup", "Blackstrap molasses", "Beet sugar", "Barley malt", "Agave Nectar/Syrup")
        val nonSugarDiet = Diet(nonSugarList, "No Sugar", false)
        val diets = listOf<Diet>(veganDiet, veggieDiet, nonSugarDiet)
        return diets
    }

    private fun addToAvoidList(etEnterIngredient: EditText)
    {
        val ingredientName = etEnterIngredient.text.toString()
        if(ingredientName.isNotEmpty()) {
            val ingredient = Ingredient(ingredientName)
            avoidListAdapter?.addIngredient(ingredient)
            etEnterIngredient.text.clear()
        }
    }

    private fun savePreferences() {
        val currentIngredients = avoidListAdapter?.let { ingredientToText(it.getIngredients()) }
        val currentDiets = dietListAdapter?.let { dietToText(it.getDiets()) }
        val updatedUserData = hashMapOf(
            "custom_ingredients" to currentIngredients,
            "diets" to currentDiets
        )
        if (userId != null) {
            db.collection("USERS").document(userId!!).update(updatedUserData as Map<String, Any>)
                .addOnSuccessListener { //Toast.makeText(activity,
                    //"Updated avoid list",
                    //Toast.LENGTH_LONG).show()
                    }
                .addOnFailureListener{ e->
                    Toast.makeText(activity,
                        "Didn't update avoid list, $e",
                        Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onPause() {
        if (dietListAdapter != null) savePreferences()
        super.onPause()
    }
}