"use strict";
const express = require('express');
const PORT = process.env.PORT || 3000;
const bodyParser = require('body-parser');
const path = require('path');
const app = express();

app.use(bodyParser.json());
app.use(express.static(`${__dirname}/../client/dist`));

app.get('*', (req, res) => {
  res.sendFile(path.resolve(`${__dirname}/../client/dist/index.html`));
});

app.listen(PORT, () => {
  console.log(`listening on port ${PORT}!`);
});