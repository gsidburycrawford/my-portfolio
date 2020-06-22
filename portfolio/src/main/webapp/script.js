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

/**
 * Adds a random interest to the page.
 */
function getComments() {
    fetch('/data').then(response => response.json()).then((comments) => {
        const commentListElement = document.getElementById('comments-container');
        commentListElement.innerHTML = '';
        commentListElement.appendChild(createListElement('Name: ' + comments.Name));
        commentListElement.appendChild(createListElement('Class: ' + comments.Class));
        commentListElement.appendChild(createListElement('Comment: ' + comments.Comment));
    });
    console.log(comments.get(0));
}

function createListElement(text) {
    const liElement = document.createElement('li');
    liElement.innerText = text;
    return liElement;
}
function addRandomInterest() {
  const interests =
      ['Politics', 'Social Justice', 'Game Development', 'Reading classic literature', ' Reddit', ' Community Service', 'Cooking Italian Food', 'Cooking Soul Food'];

  // Pick a random interest.
  const interest = interests[Math.floor(Math.random() * interests.length)];

  // Add it to the page.
  const interestContainer = document.getElementById('interest-container');
  interestContainer.innerText = interest;
}
