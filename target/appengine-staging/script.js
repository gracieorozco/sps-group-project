// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
//

var UNIQUE = "0";

function obtain_fetch_data(servlet_bool) {
    var fetch_string = ""
    if (servlet_bool == true) {
        fetch_string = "/data"
    } else {
        // fetch_string = "/search_data"
    }
    fetch(fetch_string)
      .then(response => response.text())
      .then(text => {
        var parsed_json = JSON.parse(text);
            parsed_json.forEach(post_obj => {
                // Necessary div for Bootstrap styling
                var card_div = document.createElement('div');
                card_div.setAttribute('class', 'card');

                // Necessary div for Bootstrap styling
                var card_body_div = document.createElement('div');
                card_body_div.setAttribute('class', 'card-body');

                // Creation and styling of the title as a h2 element 
                // using a function created below. It is then 
                // appended to the card body div element created above.
                var h2 = create_and_set_html_content('h2', post_obj.post_title);
                h2.setAttribute('class', 'card-title');
                card_body_div.appendChild(h2);

                // Creation and styling of the user as a p element
                var p = create_and_set_html_content('p', post_obj.user_name);
                p.setAttribute('class', 'card-subtitle mb-2 text-muted');
                card_body_div.appendChild(p);

                // Creation and styling of the post content as a p element
                p = create_and_set_html_content('p', post_obj.post_content);
                p.setAttribute('class', 'card-text');
                card_body_div.appendChild(p);

                // Creation and styling of a button as an a element
                var reply_button = create_and_set_html_content('a', 'Reply');
                reply_button.setAttribute('class', 'btn btn-primary')
                reply_button.setAttribute('style', 'color:white;');

                //START COMMENT FEATURE
                reply_button.setAttribute('href', 'new_comment.html');


                console.log("here");

                reply_button.onclick = function() {
                    doSomething(post_obj.unique_id);
                   //UNIQUE = String(post_obj.unique_id);
                   //setTimeout(doSomething, 1000, post_obj.unique_id); //this function will change the value of 
                };

                console.log("after reply button created");
                //after reply button created
                //
                console.log(post_obj.post_title);
                console.log(post_obj.unique_id);

                fetch('/comment_data') 
                    .then(response => response.text()) 
                    .then(text => {
                        var parsed_json_2 = JSON.parse(text);
                            parsed_json_2.forEach(comment_obj => {
                                if(comment_obj.post_parent_id == post_obj.unique_id){
                                    obj = create_and_set_html_content('p', "comment: " + comment_obj.comment_content);
                                    //p.setAttribute('class', 'card-text');
                                    card_body_div.appendChild(obj);
                                }
                          });
                    })
                    .catch(()=>{
                        console.log('Error!');
                    })

                console.log("after fetching comment data");

                //END COMMENT FEATURE





                card_body_div.appendChild(reply_button);

                // Append all of the elements within card_body_div into card_div
                card_div.appendChild(card_body_div);
                card_div.setAttribute('style', 'width:18rem;margin: 10px 10px 10px 10px;');
                
                // Create a br element to space out all of the posts
                // On the HTML page the function is called in, find a pre-defined
                // div named posts and append the card_div
                document.getElementById('posts').appendChild(card_div);
            });
      })
      .catch(() => {
        console.log('Error!');
      })
}

function create_and_set_html_content(element_tag, text_content) {
    // Should be used only for simple HTML tags like h1, p, a
    // Not for complex HTML tags such as forms and input
    var tag = document.createElement(element_tag);
    tag.innerHTML = text_content;
    return tag;
}

function doSomething(id) {
    console.log("in do something");
    /*
    setTimeout(doAnother, 2000, id);
    */
    UNIQUE = String(id);
    console.log(UNIQUE);
    window.name = UNIQUE;
    /*
    document.getElementById("reply-id").onload = function() {
        console.log("in being loaded");
        document.getElementById("reply-id").innerHTML = UNIQUE;
    };
    */
    console.log("about to end do something");
}

function doAnother(){
    console.log("in do another");
    document.getElementById("reply-id").innerHTML = window.name;
    console.log("about to end do another");
}

