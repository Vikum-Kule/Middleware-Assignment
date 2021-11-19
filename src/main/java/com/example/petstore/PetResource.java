package com.example.petstore;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/pets")
@Produces("application/json")
public class PetResource {
	List<Pet> pets = new ArrayList<Pet>();
	List<PetType> petTypes = new ArrayList<>();
	int petId = 1;
	int petTypeId= 1;
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {

		return Response.ok(pets).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("/{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		for(int x=0; x<pets.size(); x++){
			if (petId == pets.get(x).getPetId()){
				return Response.ok(pets.get(x)).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
		
	}

	@POST
	@Path("/add")
	public Response addPet(@RequestBody Pet pet){
		pet.setPetId(petId);
		petId++;
		pets.add(pet);
		return Response.ok(pet).build();
	}

	@DELETE
	@Path("/delete/{petId}")
	public  Response deletePet(@PathParam("petId") int petId){
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		else{
			for (int x=0; x<pets.size(); x++){
				if (petId == pets.get(x).getPetId()){
						pets.remove(x);
					return Response.ok(pets).build();
				}
			}
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("/update/{petId}")
	public Response updatePet(@PathParam("petId") int petId,@RequestBody Pet pet){
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		else{
			for (int x=0; x<pets.size(); x++){
				if (petId == pets.get(x).getPetId()){
					if (pet.getPetAge()!=null){
						pets.get(x).setPetAge(pet.getPetAge());
					}
					if (pet.getPetName()!=null){
						pets.get(x).setPetName(pet.getPetName());
					}
					if (pet.getPetType()!=null){
						pets.get(x).setPetType(pet.getPetType());
					}
					return Response.ok(pets.get(x)).build();

				}
			}
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	///********** for pet types......

	@GET
	@Path("/type/all")
	public Response getAllCategory(){
		return Response.ok(petTypes).build();
	}

	@POST
	@Path("/type/add")
	public Response addCategory (@RequestBody PetType type ){
		type.setTypeId(petTypeId);
		petTypeId++;
		petTypes.add(type);
		return Response.ok(petTypes).build();
	}

	@DELETE
	@Path("/type/delete/{typeId}")
	public Response deleteType(@PathParam("typeId") int typeId ){
		if (typeId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		else{
			for (int x=0; x<petTypes.size(); x++){
				if (typeId == petTypes.get(x).getTypeId()){
					String type = petTypes.get(x).getPetType();
					petTypes.remove(x);
					for (int y=0; y< pets.size(); y++){
						if(type.equals(pets.get(y).getPetType())){
							pets.remove(y);
							y=0;
						}
					}
					return Response.ok(petTypes).build();

				}
			}
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("type/update/{typeId}")
	public Response updateType(@PathParam("typeId") int typeId, @RequestBody PetType petType){
		if (typeId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		else{
			for (int x=0; x<petTypes.size(); x++){
				if (typeId == petTypes.get(x).getTypeId()){
					String type = petTypes.get(x).getPetType();
					petTypes.get(x).setPetType(petType.getPetType());
					for (int y=0; y< pets.size(); y++){
						if(type.equals(pets.get(y).getPetType())){
							pets.get(y).setPetType(petType.getPetType());
						}
					}
					return Response.ok(petTypes).build();

				}
			}
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/search")
	public Response petSearch(@RequestBody Search search){
		List<Pet> searchList = new ArrayList<>();
		if (search.getName()!= null){
			System.out.println("Name++++++++++++");
			for (int x =0; x<pets.size(); x++){
				if (pets.get(x).getPetName().contains(search.getName())){
					searchList.add(pets.get(x));
				}
			}
		}
		if (search.getAge() >0){
			System.out.println("Age++++"+ search.getAge());
			if (searchList.size()==0){
				for (int x =0; x<pets.size(); x++){
					System.out.println("Age++++"+ pets.get(x).getPetAge());
					if (search.getAge() == pets.get(x).getPetAge()){
						searchList.add(pets.get(x));
					}
				}
			}
			else {
				for (int x =0; x<searchList.size(); x++){
					if (search.getAge() != searchList.get(x).getPetAge()){
						searchList.remove(x);
						x=0;
					}
				}
			}
		}
		if (search.getType()!= null){
			System.out.println("Type+++++++++");
			if (searchList.size()==0){
				for (int x =0; x<pets.size(); x++){
					if (search.getType().equals(pets.get(x).getPetType())){
						searchList.add(pets.get(x));
					}
				}
			}
			else {
				for (int x =0; x<searchList.size(); x++){
					if (!search.getType().equals(searchList.get(x).getPetType())){
						searchList.remove(x);
						x=0;
					}
				}
			}
		}
		if (searchList.size()==0){
			return Response.status(Status.NOT_FOUND).build();
		}
		else {
			return Response.ok(searchList).build();
		}
	}



}
