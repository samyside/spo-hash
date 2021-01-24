const fs = require("fs");
const readline = require("readline");

const idFile = "IDs.txt";

const fileContent = fs.readFileSync(idFile, "utf-8");
const contentArray = fileContent.split("\r\n");

const idTable = new Array(510);

let i = 0;

function hash(data) {
  const str = data.split(";")[0];
  return str.charCodeAt(0) + str.charCodeAt(Math.floor(str.length / 2));
}

function nextPosition(prevPos, i) {
  return prevPos + i;
}

function setIdInTable(prevPos, data, i) {
  const pos = nextPosition(prevPos, i);
  i++;

  if (pos > idTable.length - 1) {
    console.log("Table placement error");
    return null;
  }

  if (idTable[pos] === undefined) {
    idTable[pos] = data;
    i = 0;
  } else {
    setIdInTable(pos, data, i);
  }
}

function setIdTable(content) {
  let collisions = 0;

  content.forEach((data) => {
    const elemHash = hash(data);
    const pos = elemHash - 1;

    if (idTable[pos] === undefined) {
      idTable[pos] = data;
    } else {
      setIdInTable(pos, data, i);
      collisions++;
    }
  });

  console.log("Collisions:", collisions);
}

function reHashFind(data, id, amountOfCompares) {
  amountOfCompares++;

  if (!idTable[id - 1] || id > idTable.length - 1) {
    console.log("No such ID in table");
    return amountOfCompares;
  }

  if (idTable[id - 1] && data !== idTable[id - 1].split(";")[0]) {
    const nextPos = nextPosition(id, i);
    i++;
    return reHashFind(data, nextPos, amountOfCompares);
  } else {
    console.log("Data: ", idTable[id - 1]);
    return amountOfCompares;
  }
}

function makeQuestion() {
  let amountOfCompares = 0;

  return rl.question("\r\nEnter a full name: ", (cred) => {
    const compares = reHashFind(cred, hash(cred), amountOfCompares);
    console.log("Number of compares: ", compares);

    makeQuestion();
  });
}

setIdTable(contentArray);

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

console.log(idTable);

makeQuestion();
