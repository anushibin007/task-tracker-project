FROM node:latest

# copy from my current directory to
# build context's current directory
COPY . .

# runs during building an image
RUN npm install
RUN npm run build

# runs when you run the container
CMD npm run preview

# ports to be exposed during runtime
# npm run dev - 5173
# npm run preview - 4173
EXPOSE 4173
