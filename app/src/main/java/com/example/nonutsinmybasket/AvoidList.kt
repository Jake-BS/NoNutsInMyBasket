package com.example.nonutsinmybasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AvoidList : AppCompatActivity() {

    private lateinit var avoidListAdapter: AvoidListAdapter
    private lateinit var dietListAdapter: DietListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avoid_list)

        val actionBar = supportActionBar

        actionBar!!.title = "Avoid List"
        actionBar.setDisplayHomeAsUpEnabled(true)

        settingUpAvoidList()

        settingUpDiets()
    }


    fun settingUpAvoidList(){
        avoidListAdapter = AvoidListAdapter(mutableListOf())
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
    }

    fun settingUpDiets(){
        dietListAdapter = DietListAdapter(mutableListOf(), this.avoidListAdapter)
        var rvDietList = findViewById<RecyclerView>(R.id.rvDietsList)
        rvDietList.adapter = dietListAdapter
        rvDietList.layoutManager = LinearLayoutManager(this)
        val diets = getDiets()
        for(diet in diets) dietListAdapter.addDiet(diet)
    }

    fun getDiets() : List<Diet>{
        val notVeganList = listOf<String>("Aardvark,", "Abalone,", "African", "Buffalo,", "Ahi", "Tuna,", "Albacore,", "Alligator,", "Alpaca", "Meat,", "Amphibian", "Egg,", "Ant,", "Antelope", "Meat,", "Bass,", "Bear", "Meat,", "Bee,", "Bee", "Larva,", "Beef,", "Beetle,", "Beluga", "Caviar,", "Bird", "Egg,", "Bison,", "Brain,", "Buffalo,", "Bugs,", "Carp,", "Cat", "Meat,", "Caterpillar,", "Catfish,", "Caviar,", "Chicken,", "Chicken", "Eggs,", "Clam,", "Coconut", "Grubs,", "Crab,", "Crab", "Roe,", "Crickets,", "Crocodile,", "Deer,", "Dog", "Meat,", "Donkey", "Meat,", "Duck,", "Duck", "Eggs,", "Eel,", "Eggs,", "Elk,", "Emu,", "Emu", "Egg,", "Escamole", "(Ant", "Larvae),", "Escargots,", "Fish,", "Flounder,", "Frog,", "Frog", "Eggs,", "Gnu,", "Goose,", "Goose", "Egg,", "Gopher,", "Grasshopper,", "Grouper,", "Guanaco,", "Guinea", "Fowl,", "Guinea", "Fowl", "Egg,", "Hare,", "Heart,", "Herring,", "Horse", "Meat,", "Ibex,", "Iguana,", "Jellyfish,", "Kangaroo,", "Kidney,", "Lamb,", "Leech,", "Liver,", "Lizard,", "Lizard", "Eggs,", "Llama", "Meat,", "Lobster,", "Lobster", "Roe,", "Merling,", "Moose", "Meat,", "Mouse,", "Mullet,", "Mullet", "Roe,", "Musk", "Ox,", "Mussels,", "Mutton,", "Octopus,", "Opossum,", "Ostrich,", "Ostrich", "Egg,", "Oxen,", "Oyster,", "Partridge,", "Partridge", "Egg,", "Pigeon,", "Pork,", "Prawns,", "Ptarmigan,", "Python,", "Quail,", "Quail", "Eggs,", "Rabbit,", "Raccoon,", "Rat", "Meat,", "Rattlesnake,", "Rhea,", "Rhea", "Egg,", "Roe,", "Sago", "Grubs,", "Sago", "Palm", "Weevil", "Larvae,", "Salmon,", "Scallops,", "Scorpion,", "Seal", "Meat,", "Sea", "Bass,", "Sea", "Cucumber,", "Sea", "Lion,", "Sea", "Trout,", "Sea", "Urchin,", "Shark,", "Shrimp,", "Smelt,", "Smelt", "Cavier,", "Snail,", "Snake,", "Snake", "Egg,", "Spider,", "Squid,", "Squirrel,", "Swan,", "Swan", "Egg,", "Swordfish,", "Tarantula,", "Tilapia,", "Toad,", "Trout,", "Tuna,", "Turkey,", "Turkey", "Eggs,", "Turtle,", "Turtle", "Eggs,", "Urchin,", "Urchin", "Roe,", "Venison,", "Viper,", "Walrus,", "Wasps,", "Whale", "Meat,", "Whitefish,", "Whitefish", "Roe,", "Whiting,", "Worms,", "Wtchetty", "Grubs,", "Xia", "Shrimp,", "Yak,", "Yellow", "Jacket,", "Yellowfin", "Tuna,", "Zaza-Mushi", "(Stonefly", "Larvae),", "Zebra,", "Adrenaline,", "Alanine,", "Albumen,", "Albumin,", "Alcloxa,", "Aldioxa,", "Aliphatic", "Alcohol,", "Allantoin,", "Alligator", "Skin,", "Alpha-Hydroxy", "Acids,", "Ambergris,", "Amino", "Acids,", "Aminosuccinate", "Acid,", "Angora,", "Animal", "Fats", "and", "Oils,", "Animal", "Hair,", "Arachidonic", "Acid,", "Arachidyl", "Proprionate,", "Bee", "Pollen,", "Bee", "Products,", "Beeswax.", "Honeycomb,", "Biotin.", "Vitamin", "H.", "Vitamin", "B", "Factor,", "Blood,", "Boar", "Bristles,", "Bone", "Char,", "Bone", "Meal,", "Calciferol,", "Calfskin,", "Caprylamine", "Oxide,", "Capryl", "Betaine,", "Caprylic", "Acid,", "Caprylic", "Triglyceride,", "Carbamide,", "Carmine.", "Cochineal.", "Carminic", "Acid,", "Carminic", "Acid,", "Carotene.", "Provitamin", "A.", "Beta", "Carotene,", "Casein.", "Caseinate.", "Sodium", "Caseinate,", "Caseinate,", "Cashmere,", "Castor.", "Castoreum,", "Castoreum,", "Catgut,", "Cera", "Flava,", "Cerebrosides,", "Cetyl", "Alcohol,", "Cetyl", "Palmitate,", "Chitosan,", "Cholesterin,", "Cholesterol,", "Choline", "Bitartrate,", "Civet,", "Cochineal,", "Cod", "Liver", "Oil,", "Collagen,", "Colors.", "Dyes,", "Corticosteroid,", "Cortisone.", "Corticosteroid,", "Cysteine,", "L-Form,", "Cystine,", "Dexpanthenol,", "Diglycerides,", "Dimethyl", "Stearamine,", "Down,", "Duodenum", "Substances,", "Dyes,", "Egg", "Protein,", "Elastin,", "Emu", "Oil,", "Ergocalciferol,", "Ergosterol,", "Estradiol,", "Estrogen.", "Estradiol,", "Fats,", "Fatty", "Acids,", "FD&C", "Colors,", "Feathers,", "Fish", "Liver", "Oil,", "Fish", "Oil,", "Fish", "Scales,", "Fur,", "Gel,", "Gelatin.", "Gel,", "Glucose", "Tyrosinase,", "Glycerides,", "Glycerin.", "Glycerol,", "Glycerol,", "Glyceryls,", "Glycreth-26,", "Guanine.", "Pearl", "Essence,", "Hide", "Glue,", "Honey,", "Honeycomb,", "Horsehair,", "Hyaluronic", "Acid,", "Hydrocortisone,", "Hydrolyzed", "Animal", "Protein,", "Imidazolidinyl", "Urea,", "Insulin,", "Isinglass,", "Isopropyl", "Lanolate,", "Isopropyl", "Myristate,", "Isopropyl", "Palmitate,", "Keratin,", "Lactic", "Acid,", "Lactose,", "Laneth,", "Lanogene,", "Lanolin.", "Lanolin", "Acids.", "Wool", "Fat.", "Wool", "Wax,", "Lanolin", "Alcohol,", "Lanosterols,", "Lard,", "Leather.", "Suede.", "Calfskin.", "Sheepskin.", "Alligator", "Skin.", "Other", "Types", "of", "Skin,", "Lecithin.", "Choline", "Bitartrate,", "Linoleic", "Acid,", "Lipase,", "Lipids,", "Lipoids.", "Lipids,", "Marine", "Oil,", "Methionine,", "Milk,", "Milk", "Protein,", "Mink", "Oil,", "Monoglycerides.", "Glycerides.", "(See", "Glycerin.,", "Musk", "(Oil),", "Myristal", "Ether", "Sulfate,", "Myristic", "Acid,", "Myristyls,", "\u201cNatural", "Sources.,", "Nucleic", "Acids,", "Ocenol,", "Octyl", "Dodecanol,", "Oleic", "Acid,", "Oils,", "Oleths,", "Oleyl", "Alcohol.", "Ocenol,", "Oleyl", "Arachidate,", "Oleyl", "Imidazoline,", "Oleyl", "Myristate,", "Oleyl", "Oleate,", "Oleyl", "Stearate,", "Palmitamide,", "Palmitamine,", "Palmitate,", "Palmitic", "Acid,", "Panthenol.", "Dexpanthenol.", "Vitamin", "B-Complex", "Factor.", "Provitamin", "B-5,", "Panthenyl,", "Pepsin,", "Placenta.", "Placenta", "Polypeptides", "Protein.", "Afterbirth,", "Polyglycerol,", "Polypeptides,", "Polysorbates,", "Pristane,", "Progesterone,", "Propolis,", "Provitamin", "A,", "Provitamin", "B-5,", "Provitamin", "D-2,", "Rennet.", "Rennin,", "Rennin,", "Resinous", "Glaze,", "Retinol,", "Ribonucleic", "Acid,", "RNA.", "Ribonucleic", "Acid,", "Royal", "Jelly,", "Sable", "Brushes,", "Sea", "Turtle", "Oil,", "Shark", "Liver", "Oil,", "Sheepskin,", "Shellac.", "Resinous", "Glaze,", "Silk.", "Silk", "Powder,", "Snails,", "Sodium", "Caseinate,", "Sodium", "Steroyl", "Lactylate,", "Sodium", "Tallowate,", "Spermaceti.", "Cetyl", "Palmitate.", "Sperm", "Oil,", "Sponge", "(Luna", "and", "Sea),", "Squalane,", "Squalene,", "Stearamide,", "Stearamine,", "Stearamine", "Oxide,", "Stearates,", "Stearic", "Acid,", "Stearic", "Hydrazide,", "Stearone,", "Stearoxytrimethylsilane,", "Stearoyl", "Lactylic", "Acid,", "Stearyl", "Acetate,", "Stearyl", "Alcohol.", "Sterols,", "Stearyl", "Betaine,", "Stearyl", "Caprylate,", "Stearyl", "Citrate,", "Stearyldimethyl", "Amine,", "Stearyl", "Glycyrrhetinate,", "Stearyl", "Heptanoate,", "Stearyl", "Imidazoline,", "Stearyl", "Octanoate,", "Stearyl", "Stearate,", "Steroids.", "Sterols,", "Sterols,", "Suede,", "Tallow.", "Tallow", "Fatty", "Alcohol.", "Stearic", "Acid,", "Tallow", "Acid,", "Tallow", "Amide,", "Tallow", "Amine,", "Talloweth-6,", "Tallow", "Glycerides,", "Tallow", "Imidazoline,", "Triterpene", "Alcohols,", "Turtle", "Oil.", "Sea", "Turtle", "Oil,", "Tyrosine,", "Urea.", "Carbamide,", "Uric", "Acid,", "Vitamin", "A,", "Vitamin", "B-Complex", "Factor,", "Vitamin", "B", "Factor,", "Vitamin", ",", "Vitamin", "D.", "Ergocalciferol.", "Vitamin,", "Vitamin", "H,", "Wax,", "Whey,", "Wool,", "Wool", "Fat,", "Wool", "Wax,", "Acidophilus", "Milk,", "Ammonium", "Caseinate,", "Butter,", "Butter", "Esters,", "Butter", "Fat,", "Butter", "Oil,", "Butter", "Solids,", "Buttermilk,", "Buttermilk", "Powder,", "Calcium", "Caseinate,", "Casein,", "Caseinate", "(in", "general),", "Cheese", "(All", "animal-based),", "Condensed", "Milk,", "Cottage", "Cheese,", "Cream,", "Curds,", "Custard,", "Delactosed", "Whey,", "Demineralized", "Whey,", "Dry", "Milk", "Powder,", "Dry", "Milk", "Solids,", "Evaporated", "Milk,", "Ghee,", "Goat", "Cheese,", "Goat", "Milk,", "Half", "&", "Half,", "Hydrolyzed", "Casein,", "Hydrolyzed", "Milk", "Protein,", "Iron", "Caseinate,", "Lactalbumin,", "Lactoferrin,", "Lactoglobulin,", "Lactose,", "Lactulose,", "Low-Fat", "Milk,", "Magnesium", "Caseinate,", "Malted", "Milk,", "Milk,", "Milk", "Derivative,", "Milk", "Fat,", "Milk", "Powder,", "Milk", "Protein,", "Milk", "Solids,", "Natural", "Butter", "Flavor,", "Nonfat", "Milk,", "Nougat,", "Paneer,", "Potassium", "Caseinate,", "Pudding,", "Recaldent,", "Rennet", "Casein,", "Sheep", "Milk,", "Sheep", "Milk", "Cheese,", "Skim", "Milk,", "Sodium", "Caseinate,", "Sour", "Cream,", "Sour", "Milk", "Solids,", "Sweetened", "Condensed", "Milk,", "Sweet", "Whey,", "Whey,", "Whey", "Powder,", "Whey", "Protein", "Concentrate,", "Whey", "Protein", "Hydrolysate,", "Whipped", "Cream,", "Whipped", "Topping,", "Whole", "Milk,", "Yogurt,", "Zinc", "Caseinate")
        val veganDiet = Diet(notVeganList, "Vegan", false)
        val nonVeggieList = listOf<String>("Aardvark,", "Abalone,", "African", "Buffalo,", "Ahi", "Tuna,", "Albacore,", "Alligator,", "Alpaca", "Meat,", "Amphibian", "Egg,", "Ant,", "Antelope", "Meat,", "Bass,", "Bear", "Meat,", "Bee,", "Bee", "Larva,", "Beef,", "Beetle,", "Beluga", "Caviar,", "Bison,", "Brain,", "Buffalo,", "Bugs,", "Carp,", "Cat", "Meat,", "Caterpillar,", "Catfish,", "Caviar,", "Chicken,", "Clam,", "Coconut", "Grubs,", "Crab,", "Crab", "Roe,", "Crickets,", "Crocodile,", "Deer,", "Dog", "Meat,", "Donkey", "Meat,", "Duck,", "Eel,", "Elk,", "Emu,", "Escamole", "(Ant", "Larvae),", "Escargots,", "Fish,", "Flounder,", "Frog,", "Gnu,", "Goose,", "Gopher,", "Grasshopper,", "Grouper,", "Guanaco,", "Guinea", "Fowl,", "Hare,", "Heart,", "Herring,", "Horse", "Meat,", "Ibex,", "Iguana,", "Jellyfish,", "Kangaroo,", "Kidney,", "Lamb,", "Leech,", "Liver,", "Lizard,", "Llama", "Meat,", "Lobster,", "Lobster", "Roe,", "Merling,", "Moose", "Meat,", "Mouse,", "Mullet,", "Mullet", "Roe,", "Musk", "Ox,", "Mussels,", "Mutton,", "Octopus,", "Opossum,", "Ostrich,", "Oxen,", "Oyster,", "Partridge,", "Pigeon,", "Pork,", "Prawns,", "Ptarmigan,", "Python,", "Quail,", "Rabbit,", "Raccoon,", "Rat", "Meat,", "Rattlesnake,", "Rhea,", "Roe,", "Sago", "Grubs,", "Sago", "Palm", "Weevil", "Larvae,", "Salmon,", "Scallops,", "Scorpion,", "Seal", "Meat,", "Sea", "Bass,", "Sea", "Cucumber,", "Sea", "Lion,", "Sea", "Trout,", "Sea", "Urchin,", "Shark,", "Shrimp,", "Smelt,", "Smelt", "Cavier,", "Snail,", "Snake,", "Spider,", "Squid,", "Squirrel,", "Swan,", "Swordfish,", "Tarantula,", "Tilapia,", "Toad,", "Trout,", "Tuna,", "Turkey,", "Turtle,", "Urchin,", "Urchin", "Roe,", "Venison,", "Viper,", "Walrus,", "Wasps,", "Whale", "Meat,", "Whitefish,", "Whitefish", "Roe,", "Whiting,", "Worms,", "Wtchetty", "Grubs,", "Xia", "Shrimp,", "Yak,", "Yellow", "Jacket,", "Yellowfin", "Tuna,", "Zaza-Mushi", "(Stonefly", "Larvae),", "Zebra")
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