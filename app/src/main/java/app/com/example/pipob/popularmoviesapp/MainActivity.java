package app.com.example.pipob.popularmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    BaseAdapter adapter=null;
    GridView gridView;
    Uri.Builder builder;
    String movieJson;
    String movieName[]={""};
    String urls[]={""};
    String rating[]={""};
    View v;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView= (GridView) findViewById(R.id.gridViewMovies);
        gridView.setAdapter(adapter);



    }

    public void updateMovies(){
        FetchMoviesTask moviesT = new FetchMoviesTask();
        SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(this);
        String keylocation = getString(R.string.pref_filter_key);
        String defaultLocation=getString(R.string.pref_filter_default);
        String filter = settings.getString(keylocation,defaultLocation);
        moviesT.execute(filter);

    }
    @Override
    public void onStart(){
        super.onStart();
        updateMovies();

    }
    public void setmovies(){
        adapter= new ImageAdapter(this,movieName,urls,rating);
        gridView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class FetchMoviesTask extends AsyncTask<String,Void,String[]> {
        String LOG_TAG=FetchMoviesTask.class.getSimpleName();


        @Override
        protected void onPostExecute(String[] result) {
            setmovies();
        }

        protected String[] doInBackground(String... Params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;



            //String customJson="{\"page\":1,\"results\":[{\"poster_path\":\"\\/cGOPbv9wA5gEejkUN892JrveARt.jpg\",\"adult\":false,\"overview\":\"Fearing the actions of a god-like Super Hero left unchecked, Gotham City’s own formidable, forceful vigilante takes on Metropolis’s most revered, modern-day savior, while the world wrestles with what sort of hero it really needs. And with Batman and Superman at war with one another, a new threat quickly arises, putting mankind in greater danger than it’s ever known before.\",\"release_date\":\"2016-03-23\",\"genre_ids\":[28,12,14],\"id\":209112,\"original_title\":\"Batman v Superman: Dawn of Justice\",\"original_language\":\"en\",\"title\":\"Batman v Superman: Dawn of Justice\",\"backdrop_path\":\"\\/vsjBeMPZtyB7yNsYY56XYxifaQZ.jpg\",\"popularity\":38.145498,\"vote_count\":2404,\"video\":false,\"vote_average\":5.64},{\"poster_path\":\"\\/is6QqgiPQlI3Wmk0bovqUFKM56B.jpg\",\"adult\":false,\"overview\":\"A young boxer is taken under the wing of a mob boss after his mother dies and his father is run out of town for being an abusive alcoholic.\",\"release_date\":\"2016-05-20\",\"genre_ids\":[18],\"id\":368596,\"original_title\":\"Back in the Day\",\"original_language\":\"en\",\"title\":\"Back in the Day\",\"backdrop_path\":\"\\/yySmUG29VgDdCROb9eer9L2kkKX.jpg\",\"popularity\":37.323474,\"vote_count\":33,\"video\":false,\"vote_average\":3.85},{\"poster_path\":\"\\/9KQX22BeFzuNM66pBA6JbiaJ7Mi.jpg\",\"adult\":false,\"overview\":\"We always knew they were coming back. Using recovered alien technology, the nations of Earth have collaborated on an immense defense program to protect the planet. But nothing can prepare us for the aliens’ advanced and unprecedented force. Only the ingenuity of a few brave men and women can bring our world back from the brink of extinction.\",\"release_date\":\"2016-06-22\",\"genre_ids\":[28,12,878],\"id\":47933,\"original_title\":\"Independence Day: Resurgence\",\"original_language\":\"en\",\"title\":\"Independence Day: Resurgence\",\"backdrop_path\":\"\\/8SqBiesvo1rh9P1hbJTmnVum6jv.jpg\",\"popularity\":35.517476,\"vote_count\":327,\"video\":false,\"vote_average\":4.52},{\"poster_path\":\"\\/z09QAf8WbZncbitewNk6lKYMZsh.jpg\",\"adult\":false,\"overview\":\"Dory is a wide-eyed, blue tang fish who suffers from memory loss every 10 seconds or so. The one thing she can remember is that she somehow became separated from her parents as a child. With help from her friends Nemo and Marlin, Dory embarks on an epic adventure to find them. Her journey brings her to the Marine Life Institute, a conservatory that houses diverse ocean species. Dory now knows that her family reunion will only happen if she can save mom and dad from captivity.\",\"release_date\":\"2016-06-16\",\"genre_ids\":[12,16,35],\"id\":127380,\"original_title\":\"Finding Dory\",\"original_language\":\"en\",\"title\":\"Finding Dory\",\"backdrop_path\":\"\\/iWRKYHTFlsrxQtfQqFOQyceL83P.jpg\",\"popularity\":27.219282,\"vote_count\":279,\"video\":false,\"vote_average\":6.29},{\"poster_path\":\"\\/5N20rQURev5CNDcMjHVUZhpoCNC.jpg\",\"adult\":false,\"overview\":\"Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.\",\"release_date\":\"2016-04-27\",\"genre_ids\":[28,53,878],\"id\":271110,\"original_title\":\"Captain America: Civil War\",\"original_language\":\"en\",\"title\":\"Captain America: Civil War\",\"backdrop_path\":\"\\/m5O3SZvQ6EgD5XXXLPIP1wLppeW.jpg\",\"popularity\":21.73421,\"vote_count\":2109,\"video\":false,\"vote_average\":6.88},{\"poster_path\":\"\\/tSFBh9Ayn5uiwbUK9HvD2lrRgaQ.jpg\",\"adult\":false,\"overview\":\"Beatrice Prior and Tobias Eaton venture into the world outside of the fence and are taken into protective custody by a mysterious agency known as the Bureau of Genetic Welfare.\",\"release_date\":\"2016-03-09\",\"genre_ids\":[12,878],\"id\":262504,\"original_title\":\"Allegiant\",\"original_language\":\"en\",\"title\":\"Allegiant\",\"backdrop_path\":\"\\/sFthBeT0Y3WVfg6b3MkcJs9qfzq.jpg\",\"popularity\":20.527646,\"vote_count\":469,\"video\":false,\"vote_average\":6.06},{\"poster_path\":\"\\/zSouWWrySXshPCT4t3UKCQGayyo.jpg\",\"adult\":false,\"overview\":\"Since the dawn of civilization, he was worshipped as a god. Apocalypse, the first and most powerful mutant from Marvel’s X-Men universe, amassed the powers of many other mutants, becoming immortal and invincible. Upon awakening after thousands of years, he is disillusioned with the world as he finds it and recruits a team of powerful mutants, including a disheartened Magneto, to cleanse mankind and create a new world order, over which he will reign. As the fate of the Earth hangs in the balance, Raven with the help of Professor X must lead a team of young X-Men to stop their greatest nemesis and save mankind from complete destruction.\",\"release_date\":\"2016-05-18\",\"genre_ids\":[28,12,878,14],\"id\":246655,\"original_title\":\"X-Men: Apocalypse\",\"original_language\":\"en\",\"title\":\"X-Men: Apocalypse\",\"backdrop_path\":\"\\/oQWWth5AOtbWG9o8SCAviGcADed.jpg\",\"popularity\":19.783643,\"vote_count\":1294,\"video\":false,\"vote_average\":5.99},{\"poster_path\":\"\\/inVq3FRqcYIRl2la8iZikYYxFNR.jpg\",\"adult\":false,\"overview\":\"Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.\",\"release_date\":\"2016-02-09\",\"genre_ids\":[28,12,35,10749],\"id\":293660,\"original_title\":\"Deadpool\",\"original_language\":\"en\",\"title\":\"Deadpool\",\"backdrop_path\":\"\\/nbIrDhOtUpdD9HKDBRy02a8VhpV.jpg\",\"popularity\":17.92622,\"vote_count\":4088,\"video\":false,\"vote_average\":7.13},{\"poster_path\":\"\\/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg\",\"adult\":false,\"overview\":\"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.\",\"release_date\":\"2015-06-09\",\"genre_ids\":[28,12,878,53],\"id\":135397,\"original_title\":\"Jurassic World\",\"original_language\":\"en\",\"title\":\"Jurassic World\",\"backdrop_path\":\"\\/dkMD5qlogeRMiEixC4YNPUvax2T.jpg\",\"popularity\":16.910038,\"vote_count\":4630,\"video\":false,\"vote_average\":6.62},{\"poster_path\":\"\\/kqjL17yufvn9OVLyXYpvtyrFfak.jpg\",\"adult\":false,\"overview\":\"An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. There's Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.\",\"release_date\":\"2015-05-13\",\"genre_ids\":[28,12,878,53],\"id\":76341,\"original_title\":\"Mad Max: Fury Road\",\"original_language\":\"en\",\"title\":\"Mad Max: Fury Road\",\"backdrop_path\":\"\\/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg\",\"popularity\":16.16893,\"vote_count\":4801,\"video\":false,\"vote_average\":7.32},{\"poster_path\":\"\\/sM33SANp9z6rXW8Itn7NnG1GOEs.jpg\",\"adult\":false,\"overview\":\"Determined to prove herself, Officer Judy Hopps, the first bunny on Zootopia's police force, jumps at the chance to crack her first case - even if it means partnering with scam-artist fox Nick Wilde to solve the mystery.\",\"release_date\":\"2016-02-11\",\"genre_ids\":[16,12,10751,35],\"id\":269149,\"original_title\":\"Zootopia\",\"original_language\":\"en\",\"title\":\"Zootopia\",\"backdrop_path\":\"\\/mhdeE1yShHTaDbJVdWyTlzFvNkr.jpg\",\"popularity\":14.51764,\"vote_count\":1372,\"video\":false,\"vote_average\":7.39},{\"poster_path\":\"\\/gj282Pniaa78ZJfbaixyLXnXEDI.jpg\",\"adult\":false,\"overview\":\"Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol.\",\"release_date\":\"2014-11-18\",\"genre_ids\":[878,12,53],\"id\":131631,\"original_title\":\"The Hunger Games: Mockingjay - Part 1\",\"original_language\":\"en\",\"title\":\"The Hunger Games: Mockingjay - Part 1\",\"backdrop_path\":\"\\/83nHcz2KcnEpPXY50Ky2VldewJJ.jpg\",\"popularity\":13.872827,\"vote_count\":2954,\"video\":false,\"vote_average\":6.75},{\"poster_path\":\"\\/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg\",\"adult\":false,\"overview\":\"Beatrice Prior must confront her inner demons and continue her fight against a powerful alliance which threatens to tear her society apart.\",\"release_date\":\"2015-03-18\",\"genre_ids\":[12,878,53],\"id\":262500,\"original_title\":\"Insurgent\",\"original_language\":\"en\",\"title\":\"Insurgent\",\"backdrop_path\":\"\\/L5QRL1O3fGs2hH1LbtYyVl8Tce.jpg\",\"popularity\":13.806751,\"vote_count\":1988,\"video\":false,\"vote_average\":6.48},{\"poster_path\":\"\\/6FxOPJ9Ysilpq0IgkrMJ7PubFhq.jpg\",\"adult\":false,\"overview\":\"Tarzan, having acclimated to life in London, is called back to his former home in the jungle to investigate the activities at a mining encampment.\",\"release_date\":\"2016-06-30\",\"genre_ids\":[28,12],\"id\":258489,\"original_title\":\"The Legend of Tarzan\",\"original_language\":\"en\",\"title\":\"The Legend of Tarzan\",\"backdrop_path\":\"\\/75GFqrnHMKqkcNZ2wWefWXfqtMV.jpg\",\"popularity\":13.430036,\"vote_count\":100,\"video\":false,\"vote_average\":4.03},{\"poster_path\":\"\\/h28t2JNNGrZx0fIuAw8aHQFhIxR.jpg\",\"adult\":false,\"overview\":\"A recently cheated on married woman falls for a younger man who has moved in next door, but their torrid affair soon takes a dangerous turn.\",\"release_date\":\"2015-01-23\",\"genre_ids\":[53],\"id\":241251,\"original_title\":\"The Boy Next Door\",\"original_language\":\"en\",\"title\":\"The Boy Next Door\",\"backdrop_path\":\"\\/vj4IhmH4HCMZYYjTMiYBybTWR5o.jpg\",\"popularity\":13.193211,\"vote_count\":408,\"video\":false,\"vote_average\":4.71},{\"poster_path\":\"\\/e3lBJCedHnZPfNfmBArKHZXXNC0.jpg\",\"adult\":false,\"overview\":\"Lorraine and Ed Warren travel to north London to help a single mother raising four children alone in a house plagued by malicious spirits.\",\"release_date\":\"2016-06-08\",\"genre_ids\":[27],\"id\":259693,\"original_title\":\"The Conjuring 2\",\"original_language\":\"en\",\"title\":\"The Conjuring 2\",\"backdrop_path\":\"\\/mj9jHyMvHfJ3oUZrisCoubItATk.jpg\",\"popularity\":13.125327,\"vote_count\":319,\"video\":false,\"vote_average\":6.13},{\"poster_path\":\"\\/ckrTPz6FZ35L5ybjqvkLWzzSLO7.jpg\",\"adult\":false,\"overview\":\"The peaceful realm of Azeroth stands on the brink of war as its civilization faces a fearsome race of invaders: orc warriors fleeing their dying home to colonize another. As a portal opens to connect the two worlds, one army faces destruction and the other faces extinction. From opposing sides, two heroes are set on a collision course that will decide the fate of their family, their people, and their home.\",\"release_date\":\"2016-05-25\",\"genre_ids\":[28,12,14],\"id\":68735,\"original_title\":\"Warcraft\",\"original_language\":\"en\",\"title\":\"Warcraft\",\"backdrop_path\":\"\\/5SX2rgKXZ7NVmAJR5z5LprqSXKa.jpg\",\"popularity\":12.865487,\"vote_count\":459,\"video\":false,\"vote_average\":6.01},{\"poster_path\":\"\\/MZFPacfKzgisnPoJIPEFZUXBBT.jpg\",\"adult\":false,\"overview\":\"Continuing his \\\"legendary adventures of awesomeness\\\", Po must face two hugely epic, but different threats: one supernatural and the other a little closer to his home.\",\"release_date\":\"2016-01-23\",\"genre_ids\":[28,12,16,35,10751],\"id\":140300,\"original_title\":\"Kung Fu Panda 3\",\"original_language\":\"en\",\"title\":\"Kung Fu Panda 3\",\"backdrop_path\":\"\\/eHWmEUP4fa7h1Fe7TXfTL7ncDl8.jpg\",\"popularity\":12.732752,\"vote_count\":631,\"video\":false,\"vote_average\":6.42},{\"poster_path\":\"\\/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg\",\"adult\":false,\"overview\":\"Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.\",\"release_date\":\"2014-11-05\",\"genre_ids\":[12,18,878],\"id\":157336,\"original_title\":\"Interstellar\",\"original_language\":\"en\",\"title\":\"Interstellar\",\"backdrop_path\":\"\\/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg\",\"popularity\":12.424755,\"vote_count\":5205,\"video\":false,\"vote_average\":8.17},{\"poster_path\":\"\\/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg\",\"adult\":false,\"overview\":\"The year is 2029. John Connor, leader of the resistance continues the war against the machines. At the Los Angeles offensive, John's fears of the unknown future begin to emerge when TECOM spies reveal a new plot by SkyNet that will attack him from both fronts; past and future, and will ultimately change warfare forever.\",\"release_date\":\"2015-06-23\",\"genre_ids\":[878,28,53,12],\"id\":87101,\"original_title\":\"Terminator Genisys\",\"original_language\":\"en\",\"title\":\"Terminator Genisys\",\"backdrop_path\":\"\\/bIlYH4l2AyYvEysmS2AOfjO7Dn8.jpg\",\"popularity\":11.322237,\"vote_count\":2160,\"video\":false,\"vote_average\":5.96}],\"total_results\":19649,\"total_pages\":983}";

            try {

                builder = new Uri.Builder();
                builder.scheme("http");

                builder.authority("api.themoviedb.org");
                builder.appendPath("3");
                builder.appendPath("movie");
                builder.appendPath(Params[0]);
                builder.appendQueryParameter("api_key",getString(R.string.apiMovieKey));
                //builder.appendQueryParameter("sort_by", "popularity.desc");
                URL url = new URL(builder.build().toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                movieJson = buffer.toString();
                MovieParser movieparser = new MovieParser();
                try{
                    movieparser.getMovieDataFromJson(movieJson);
                    movieName =movieparser.getMovieNames();
                    urls=movieparser.getUrlImages();
                    rating=movieparser.getRating();
                }catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

               return null;
            }  finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }


            return null;
        }

    }


}
