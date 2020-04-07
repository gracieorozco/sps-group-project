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

var reply_button_id = null;

function obtain_fetch_data() {
  fetch('/data')
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



                //addition necessary for comments - will link to comment html
                reply_button.setAttribute('href', 'new_comment.html');

                //run this function when clicked
                /*
                reply_button.onclick = function() {
                    uniqueReplyButtonId( (post_obj.post_id).toString(10)); 
                };

                //iterate through comments
                var comment_arr = post_obj.comments;
                for(int i = 0; i < comment_arr.length; i++){
                    p = create_and_set_html_content('p', "comment: " + comment_arr[i]);
                    p.setAttribute('class', 'card-text');
                    card_body_div.appendChild(p);     
                }

                */
                


                //fetch from comments
                fetch('/comment_data')
                  .then(response => response.text())
                  .then(text => {
                    var parsed_json = JSON.parse(text);
                        parsed_json.forEach(comment_obj => {
                        // Necessary div for Bootstrap styling
                        //var card_div = document.createElement('div');
                        //card_div.setAttribute('class', 'card');
                        // Necessary div for Bootstrap styling
                       // var card_body_div = document.createElement('div');
                        //card_body_div.setAttribute('class', 'card-body');
                        p = create_and_set_html_content('p', "comment: " + comment_obj.comment_content);
                        p.setAttribute('class', 'card-text');
                        card_body_div.appendChild(p);     


                            // Creation and styling of the post content as a p element
                                          // Append all of the elements within card_body_div into card_div
                            //card_div.appendChild(card_body_div);
                            // Create a br element to space out all of the posts
                            // On the HTML page the function is called in, find a pre-defined
                            // div named posts and append the card_div
                            //document.getElementById('posts').appendChild(card_div);
                        });
                  })
                  .catch(() => {
                    console.log('Error!');
                  })







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

function uniqueReplyButtonId(str){
    document.getElementById("postID").value = str;
}


